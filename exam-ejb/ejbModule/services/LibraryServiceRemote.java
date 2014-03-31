package services;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import model.Book;
import model.Category;
import model.Customer;

@Remote
public interface LibraryServiceRemote {

	void create(Book book);

	void create(Customer customer);

	void create(Category category);

	void assignBooksToCategory(List<Book> books, Category category);

	void loanBookToCustomer(Customer customer, Book book, Date startDate, int duration);

	List<Category> findCategoriesByCustomer(Customer customer);

	List<Customer> findCustomersByCategory(Category category);

	List<Book> sortBooksByPopularity();

}
