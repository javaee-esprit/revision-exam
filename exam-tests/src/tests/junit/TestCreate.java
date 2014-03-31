package tests.junit;

import static junit.framework.Assert.assertEquals;
import helpers.ScriptHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.Book;
import model.Category;
import model.Customer;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import services.LibraryServiceRemote;
import ds.ConnectionFactory;

public class TestCreate {
	
	
	private static LibraryServiceRemote service = null;
	
	

	@BeforeClass
	public static void initUniversityService(){
		try {
			service  = (LibraryServiceRemote)new InitialContext().lookup("ejb:/exam-ejb/LibraryService!services.LibraryServiceRemote");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Test
	public void _01_itShouldCreateCategory(){
		Category cat1 = new Category(1, "Biographies");
		service.create(cat1);
		verifyCategoryIsInDB();
	}
	
	@Test
	public void _02_itShouldCreateBook(){
		Book b1 = new Book(1, "Justice League");
		service.create(b1);
		verifyBookIsInDB();
	}
	
	@Test
	public void _03_itShouldCreateCustomer(){
		Customer cust1 = new Customer(1, "mohamed");
		service.create(cust1);
		verifyCustomerIsInDB();
	}
	
	
	@Test
	public void _04_itShouldCreateCategoryWithItsBooks(){
		Category cat2 = new Category(2, "Computers & Technology");
		Book b3 = new Book(3, "Python for Kids");
		Book b5 = new Book(5, "Lean UX");
		List<Book> cat2Books = new ArrayList<Book>();
		cat2Books.add(b3);cat2Books.add(b5);
		cat2.setBooks(cat2Books);
		b3.setCategory(cat2);b5.setCategory(cat2);
		service.create(cat2);
		verifyCategoryAndBooksIsInDB();
	}

	




	
	
	private void verifyCategoryIsInDB() {
		verifyCategoryIsInDB(1, "Biographies");
	}
	
	
	private void verifyBookIsInDB() {
		verifyBookIsInDB(1, "Justice League");
	}
	
	private void verifyCustomerIsInDB() {
		verifyCustomerInDB(1, "mohamed");
	}
	
	private void verifyCategoryAndBooksIsInDB() {
		verifyCategoryIsInDB(2, "Computers & Technology");
		verifyBookIsInDB(3, "Python for Kids");
		verifyBookIsInDB(5, "Lean UX");
		verifyBookWasAssignedToCategory(3, 2);
		verifyBookWasAssignedToCategory(5, 2);
		
	}




	private static ScriptHelper scriptHelper = null;
	private static Connection connection = null;
	
	@BeforeClass
	public static void initTestUtilities(){
		scriptHelper = ScriptHelper.getInstance();
		connection = ConnectionFactory.getInstance().createConnection("./conf/app-ds.xml");
	}
	@Before
	public void setUp(){
		
	}
	@After
	public void tearDown(){
		scriptHelper.executeScript("DELETE FROM T_CUSTOMER", connection);
		scriptHelper.executeScript("DELETE FROM T_BOOK", connection);
		scriptHelper.executeScript("DELETE FROM T_CATEGORY", connection);
	}
	
	
	
	
	private void verifyBookIsInDB(int id, String name) {
		String foundName = null;
		String sql = "SELECT * FROM T_BOOK WHERE ID=?"; 
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery(); 
			
			
			System.out.println(sql);
			if (resultSet.next()) {
				foundName = resultSet.getString("NAME");
			}
			
			
		} catch (SQLException ex) {
			Logger.getLogger(TestCreate.class.getName()).log(Level.SEVERE, "failed", ex);
		} finally {
			
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException ex) {
				Logger.getLogger(TestCreate.class.getName()).log(Level.SEVERE, "failed", ex);
			}
		}
		assertEquals(name, foundName);
		
	}
	
	private void verifyCategoryIsInDB(int id, String name) {
		String foundName = null;
		String sql = "SELECT * FROM T_CATEGORY WHERE ID=?"; 
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery(); 
			
			
			System.out.println(sql);
			if (resultSet.next()) {
				foundName = resultSet.getString("NAME");
			}
			
			
		} catch (SQLException ex) {
			Logger.getLogger(TestCreate.class.getName()).log(Level.SEVERE, "failed", ex);
		} finally {
			
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException ex) {
				Logger.getLogger(TestCreate.class.getName()).log(Level.SEVERE, "failed", ex);
			}
		}
		assertEquals(name, foundName);
		
	}
	
	private void verifyCustomerInDB(int id, String name) {
		String foundName = null;
		String sql = "SELECT * FROM T_CUSTOMER WHERE ID=?"; 
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery(); 
			
			
			System.out.println(sql);
			if (resultSet.next()) {
				foundName = resultSet.getString("NAME");
			}
			
			
		} catch (SQLException ex) {
			Logger.getLogger(TestCreate.class.getName()).log(Level.SEVERE, "failed", ex);
		} finally {
			
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException ex) {
				Logger.getLogger(TestCreate.class.getName()).log(Level.SEVERE, "failed", ex);
			}
		}
		assertEquals(name, foundName);
		
	}
	
	private void verifyBookWasAssignedToCategory(int idBook, int idCategory) {
		String sql = "SELECT * FROM T_BOOK WHERE ID=?";
		int foundCategoryFK = -1;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idBook);
			resultSet = preparedStatement.executeQuery();

			System.out.println(sql);
			if (resultSet.next()) {
				foundCategoryFK = resultSet.getInt("CATEGORY_FK");
			}

		} catch (SQLException ex) {
			Logger.getLogger(TestAssignBooksToCategories.class.getName()).log(Level.SEVERE, "failed", ex);
		} finally {

			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException ex) {
				Logger.getLogger(TestAssignBooksToCategories.class.getName()).log(Level.SEVERE, "failed", ex);
			}
		}
		assertEquals(idCategory, foundCategoryFK);
		
	}
	
}
