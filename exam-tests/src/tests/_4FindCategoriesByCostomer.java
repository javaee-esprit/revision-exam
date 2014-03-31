package tests;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.Category;
import model.Customer;

import services.LibraryServiceRemote;

public class _4FindCategoriesByCostomer {
	
	public static void main(String[] args) {
		
		LibraryServiceRemote service = null;
		try {
			service = (LibraryServiceRemote)new InitialContext().lookup("ejb:/exam-ejb/LibraryService!services.LibraryServiceRemote");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		Customer cust1 = new Customer(1, "mohamed");
		List<Category> categories = service.findCategoriesByCustomer(cust1);
		for(Category cat:categories)
			System.out.println(cat);
	}

}
