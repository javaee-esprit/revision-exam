package tests;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.Category;
import model.Customer;

import services.LibraryServiceRemote;

public class _5FindCustomersByCategory {
	
	public static void main(String[] args) {
		
		LibraryServiceRemote service = null;
		try {
			service = (LibraryServiceRemote)new InitialContext().lookup("ejb:/exam-ejb/LibraryService!services.LibraryServiceRemote");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		Category cat2 = new Category(2, "Computers & Technology");
		List<Customer> customers = service.findCustomersByCategory(cat2);
		for(Customer cust:customers)
			System.out.println(cust);
	}

}
