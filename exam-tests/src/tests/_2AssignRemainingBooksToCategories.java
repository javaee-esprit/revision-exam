package tests;

import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.Book;
import model.Category;
import services.LibraryServiceRemote;

public class _2AssignRemainingBooksToCategories {
	
	public static void main(String[] args) {
		LibraryServiceRemote service = null;
		try {
			service = (LibraryServiceRemote)new InitialContext().lookup("ejb:/exam-ejb/LibraryService!services.LibraryServiceRemote");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		Category cat1 = new Category(1, "Biographies");
		Category cat3 = new Category(3, "Comics");
		
		Book b1 = new Book(1, "Justice League");
		Book b2 = new Book(2, "Thomas Jefferson: The Art of Power");
		Book b4 = new Book(4, "Avengers");
		
		List<Book> books1 = new ArrayList<Book>();
		books1.add(b1); books1.add(b4);
		
		
		List<Book> books3 = new ArrayList<Book>();
		books3.add(b2);
		
		service.assignBooksToCategory(books1, cat3);
		service.assignBooksToCategory(books3, cat1);
		
		
		
		
	}

}
