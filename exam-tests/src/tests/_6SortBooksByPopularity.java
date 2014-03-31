package tests;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.Book;

import services.LibraryServiceRemote;

public class _6SortBooksByPopularity {
	
	public static void main(String[] args) {
		LibraryServiceRemote service = null;
		try {
			service = (LibraryServiceRemote)new InitialContext().lookup("ejb:/exam-ejb/LibraryService!services.LibraryServiceRemote");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		List<Book> sortedBooks = service.sortBooksByPopularity();
		int i = 1;
		for(Book book: sortedBooks)
			System.out.println(i++ +"-"+book);
	}

}
