package tests;

import helpers.DateHelper;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.Book;
import model.Customer;
import services.LibraryServiceRemote;

public class _3MakeSomeLoans {
	
	public static void main(String[] args) {
		
		
		LibraryServiceRemote service = null;
		try {
			service = (LibraryServiceRemote)new InitialContext().lookup("ejb:/exam-ejb/LibraryService!services.LibraryServiceRemote");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		
		
		Book b1 = new Book(1, "Justice League");
		Book b3 = new Book(3, "Python for Kids");
		Book b4 = new Book(4, "Avengers");
		Book b5 = new Book(5, "Lean UX");
		
		
		Customer cust1 = new Customer(1, "mohamed");
		Customer cust2 = new Customer(2, "amina");
		Customer cust3 = new Customer(3, "slim");
		
		service.loanBookToCustomer(cust1, b3, DateHelper.toDate("01/01/11 09:30:00"), 14);
		service.loanBookToCustomer(cust1, b4, DateHelper.toDate("15/10/11 20:30:00"), 5);
		service.loanBookToCustomer(cust3, b5, DateHelper.toDate("25/07/12 17:23:00"), 20);
		service.loanBookToCustomer(cust2, b1, DateHelper.toDate("17/12/12 13:20:00"), 12);
		service.loanBookToCustomer(cust2, b1, DateHelper.toDate("07/12/12 10:12:00"), 30);
		service.loanBookToCustomer(cust2, b4, DateHelper.toDate("07/11/12 10:13:00"), 30);
		service.loanBookToCustomer(cust2, b1, DateHelper.toDate("07/11/12 10:14:00"), 30);
	}

}
