package tests.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ JPQLTests.class, TestAssignBooksToCategories.class,
		TestCreate.class, TestMakeALoan.class })
public class AllTests {
	
	

}
