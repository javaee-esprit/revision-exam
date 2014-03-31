package tests.junit;

import static junit.framework.Assert.assertEquals;
import helpers.DateHelper;
import helpers.ScriptHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.Book;
import model.Customer;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import services.LibraryServiceRemote;
import ds.ConnectionFactory;

public class TestMakeALoan {
	
	
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
	public void _06_itShouldMakeALoan() {
		
		
		Customer customer = new Customer(2, "amina");
		Book book = new Book(5, "Lean UX");
		Date startDate = DateHelper.toDate("14/01/11 17:18:00");
		int duration = 12;
		
		
		service.loanBookToCustomer(customer, book, startDate, duration);
		
		verifyCustomerAndBookAssociation();
	}


	private void verifyCustomerAndBookAssociation() {
		verifyCustomerInDB(2, "amina");
		verifyBookIsInDB(5, "Lean UX");
		verifyLoanIsInDB(5, 2, DateHelper.toDate("14/01/11 17:18:00"), 12);
		
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
	}

	@Before
	public void setUp() {
		scriptHelper
				.executeScript(
				"INSERT INTO T_BOOK (ID,NAME) VALUES ('5', 'Lean UX');",
				connection);
		scriptHelper.executeScript(
				"INSERT INTO T_CUSTOMER (ID,NAME) VALUES ('2', 'amina');",
				connection);
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
			Logger.getLogger(TestMakeALoan.class.getName()).log(Level.SEVERE, "failed", ex);
		} finally {
			
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException ex) {
				Logger.getLogger(TestMakeALoan.class.getName()).log(Level.SEVERE, "failed", ex);
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
			Logger.getLogger(TestMakeALoan.class.getName()).log(Level.SEVERE, "failed", ex);
		} finally {
			
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException ex) {
				Logger.getLogger(TestMakeALoan.class.getName()).log(Level.SEVERE, "failed", ex);
			}
		}
		assertEquals(name, foundName);
		
	}
	
	
	
	
	private void verifyLoanIsInDB(int bookFK, int customerFK, Date startDate, int duration) {
		String sql = "SELECT * FROM T_LOAN WHERE BOOK_FK=? AND CUSTOMER_FK=? AND START_DATE=?";
		int foundDuration = -999;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, bookFK);
			preparedStatement.setInt(2, customerFK);
			preparedStatement.setTimestamp(3, new Timestamp(startDate.getTime()));
			resultSet = preparedStatement.executeQuery();

			System.out.println(sql);
			if (resultSet.next()) {
				foundDuration = resultSet.getInt("DURATION");
			}

		} catch (SQLException ex) {
			Logger.getLogger(TestMakeALoan.class.getName()).log(Level.SEVERE, "failed", ex);
		} finally {

			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException ex) {
				Logger.getLogger(TestMakeALoan.class.getName()).log(Level.SEVERE, "free resourses failed", ex);
			}
		}
		assertEquals(duration, foundDuration);
	}
	
	
	
	
	
	

}
