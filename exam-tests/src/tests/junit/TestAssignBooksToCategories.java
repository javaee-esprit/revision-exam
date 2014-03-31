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

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import services.LibraryServiceRemote;
import ds.ConnectionFactory;

public class TestAssignBooksToCategories {
	
	
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
	public void _05_itSouldAssignBooksToCategory() {
		
		
		Category cat3 = new Category(3, "Comics");
		Book b1 = new Book(1, "Justice League");
		Book b4 = new Book(4, "Avengers");
		List<Book> books1 = new ArrayList<Book>();
		books1.add(b1); books1.add(b4);
		
		
		service.assignBooksToCategory(books1, cat3);
		
		verifyBooksWereAssignedToCategory();
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
		scriptHelper.executeScript("DELETE FROM T_BOOK", connection);
		scriptHelper.executeScript("DELETE FROM T_CATEGORY", connection);
	}

	@Before
	public void setUp() {
		scriptHelper
				.executeScript(
				"INSERT INTO T_BOOK (ID,NAME) VALUES ('1', 'Justice League'),('4', 'Avengers');",
				connection);
		scriptHelper.executeScript(
				"INSERT INTO T_CATEGORY (ID,NAME) VALUES ('3', 'Comics');",
				connection);
	}
	private void verifyBooksWereAssignedToCategory() {
		verifyCategoryIsInDB(3, "Comics");
		verifyBookIsInDB(1, "Justice League");
		verifyBookIsInDB(4, "Avengers");
		verifyBookWasAssignedToCategory(1, 3);
		verifyBookWasAssignedToCategory(4, 3);
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
