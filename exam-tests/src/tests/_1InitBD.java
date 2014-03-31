package tests;

import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.Book;
import model.Category;
import model.Customer;

import services.LibraryServiceRemote;

public class _1InitBD {
	
	public static void main(String[] args) {
		LibraryServiceRemote service = null;
		try {
			service = (LibraryServiceRemote)new InitialContext().lookup("ejb:/exam-ejb/LibraryService!services.LibraryServiceRemote");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		Category cat1 = new Category(1, "Biographies");
		Category cat2 = new Category(2, "Computers & Technology");
		Category cat3 = new Category(3, "Comics");
		
		Book b1 = new Book(1, "Justice League");
		Book b2 = new Book(2, "Thomas Jefferson: The Art of Power");
		Book b3 = new Book(3, "Python for Kids");
		Book b4 = new Book(4, "Avengers");
		Book b5 = new Book(5, "Lean UX");
		
		Customer cust1 = new Customer(1, "mohamed");
		Customer cust2 = new Customer(2, "amina");
		Customer cust3 = new Customer(3, "slim");
		
		service.create(cust1);
		service.create(cust2);
		service.create(cust3);
		
		
		
		service.create(b1);
		service.create(b2);
		service.create(b4);
		
		service.create(cat1);
		service.create(cat3);
		
		
		List<Book> cat2Books = new ArrayList<Book>();
		cat2Books.add(b3);cat2Books.add(b5);
		cat2.setBooks(cat2Books);
		b3.setCategory(cat2);b5.setCategory(cat2);
		
		service.create(cat2);
		
		
	}

}
