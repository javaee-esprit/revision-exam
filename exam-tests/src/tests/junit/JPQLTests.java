package tests.junit;

import helpers.ScriptHelper;

import java.sql.Connection;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.Book;
import model.Category;
import model.Customer;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import services.LibraryServiceRemote;

import ds.ConnectionFactory;

public class JPQLTests {

	
private static LibraryServiceRemote service = null;
	
	private Category cat2 = new Category(2, "Computers & Technology");
	private Category cat3 = new Category(3, "Comics");
	private Customer cust1 = new Customer(1, "mohamed");
	private Customer cust3 = new Customer(3, "slim");

	@BeforeClass
	public static void initUniversityService(){
		try {
			service  = (LibraryServiceRemote)new InitialContext().lookup("ejb:/exam-ejb/LibraryService!services.LibraryServiceRemote");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void _07_itShouldFindCategoriesByCustomer() {
		Customer customer = new Customer(1, "mohamed");
		List<Category> categories = service.findCategoriesByCustomer(customer);
		verifyListcontains(categories,cat2,cat3);
		
	}
	
	@Test
	public void _08_itShouldFindCustomersByCategory() {
		Category category = new  Category(2, "Computers & Technology");
		List<Customer> customers = service.findCustomersByCategory(category);
		verifyListcontains(customers,cust1,cust3);
		
	}
	
	

	@Test
	public void _09_itShouldSortBooksByPopularity() {
		List<Book> sortedBooks = service.sortBooksByPopularity();
		verifyBooksAreInOrderOfPopularity(sortedBooks);
		
	}
	
	



	private static ScriptHelper scriptHelper = null;
	private static Connection connection = null;

	@BeforeClass
	public static void initTestUtilities() {
		scriptHelper = ScriptHelper.getInstance();
		connection = ConnectionFactory.getInstance().createConnection(
				"./conf/app-ds.xml");
	}

	@After
	public void tearDown() {
		scriptHelper.executeScript("DELETE FROM T_LOAN", connection);
		scriptHelper.executeScript("DELETE FROM T_CUSTOMER", connection);
		scriptHelper.executeScript("DELETE FROM T_BOOK", connection);
		scriptHelper.executeScript("DELETE FROM T_CATEGORY", connection);
	}

	@Before
	public void setUp() {
		
		scriptHelper.executeScript("INSERT INTO T_CUSTOMER (ID,NAME) VALUES ('1','mohamed'),('2','amina'),('3','slim');", connection);
		scriptHelper.executeScript("INSERT INTO T_CATEGORY (ID,NAME) VALUES ('1','Biographies'),('2','Computers & Technology'),('3','Comics');", connection);
		scriptHelper.executeScript("INSERT INTO T_BOOK (ID,NAME,CATEGORY_FK) VALUES ('1','Justice League','3'),('2','Thomas Jefferson: The Art of Power','1'),('3','Python for Kids','2'),('4','Avengers','3'),('5','Lean UX','2')", connection);
		scriptHelper.executeScript("INSERT INTO T_LOAN(BOOK_FK, CUSTOMER_FK, START_DATE, DURATION) VALUES('1','2','2012-11-07 10:14:00','30'),('1','2','2012-12-07 10:12:00','30'),('1','2','2012-12-17 13:20:00','12'),('3','1','2011-01-01 09:30:00','14'),('4','1','2011-10-15 20:30:00','5'),('4','2','2012-11-07 10:13:00','30'),('5','3','2012-07-25 17:23:00','20');", connection); 
	}
	
	
	
	private void verifyListcontains(List<Category> categories, Category... cs) {
		assertEquals(cs.length, categories.size());
		for(Category c:cs){
			boolean found = false;
			for(Category category:categories){
				if((c.getId() == category.getId()) 
						&& (c.getName().equals(category.getName()))){
					found = true;
				}
			}
			assertTrue(found);
		}
	}
	
	private void verifyListcontains(List<Customer> customers, Customer... cs) {
		assertEquals(cs.length, customers.size());
		for(Customer c:cs){
			boolean found = false;
			for(Customer customer:customers){
				if((c.getId() == customer.getId()) 
						&& (c.getName().equals(customer.getName()))){
					found = true;
				}
			}
			assertTrue(found);
		}
	}
	
	private void verifyBooksAreInOrderOfPopularity(List<Book> sortedBooks) {
		assertEquals(1,sortedBooks.get(0).getId());
		assertEquals(4,sortedBooks.get(1).getId());
		assertEquals(3,sortedBooks.get(2).getId());
		assertEquals(5,sortedBooks.get(3).getId());
		assertEquals(2,sortedBooks.get(4).getId());
	}
}
