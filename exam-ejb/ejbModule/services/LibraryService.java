package services;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Book;
import model.Category;
import model.Customer;
import model.Loan;

@Stateless
public class LibraryService implements LibraryServiceRemote {
	
	@PersistenceContext
	private EntityManager em;

    public LibraryService() {
    }

	public void create(Book book) {
		em.persist(book);
	}

	public void create(Customer customer) {
		em.persist(customer);
	}

	public void create(Category category) {
		em.persist(category);
	}

	public void assignBooksToCategory(List<Book> books, Category category) {
		for(Book book:books){
			book.setCategory(category);
			em.merge(book);
		}
	}

	public void loanBookToCustomer(Customer customer, Book book,Date startDate, int duration) {
		Loan loan = new Loan(customer, book, startDate, duration);
		em.persist(loan);
	}

	public List<Category> findCategoriesByCustomer(Customer customer) {
		return em.createQuery("select distinct c from Category c join c.books b join b.loans l where l.customer=:x").setParameter("x", customer).getResultList();
	}

	public List<Customer> findCustomersByCategory(Category category) {
		return em.createQuery("select distinct c from Customer c join c.loans l join l.book b where b.category=:x").setParameter("x", category).getResultList();
	}

	public List<Book> sortBooksByPopularity() {
		
		return em.createQuery("select b from Book b order by size(b.loans) desc").getResultList();
	}

}
