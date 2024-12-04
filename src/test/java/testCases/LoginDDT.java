package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import Utilities.DataProviders;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

/*
 * Data is valid - login success - test pass - logout
 * Data is valid - login failed - test fail
 * 
 *  Data is invalid - login success - test fail - logout
 *  Data is invalid - login failed - test pass
 */

public class LoginDDT extends BaseClass {
	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups = "Ddt")
	public void validate_loginDDT(String email, String password, String dataType) {

		logger.info("*** Stating LoginDDT Test ***");
		logger.debug("*** Capturing application debug logs ***");
		try {
			// create a object of HomePage
			HomePage homePage = new HomePage(driver);
			homePage.clickOnMyAccount();
			homePage.clickOnLogin();

			// loginPage
			LoginPage loginPage = new LoginPage(driver);
			loginPage.setEmail(email);
			loginPage.setPassword(password);
			loginPage.clickLogin();

			// My account
			MyAccountPage myAcctPage = new MyAccountPage(driver);
			boolean targetPage = myAcctPage.isMyAccountPageExists();

			if (dataType.equalsIgnoreCase("valid")) {
				if (targetPage == true) {
					myAcctPage.clickOnLogOut();
					Assert.assertTrue(true);
				} else {
					Assert.fail();
				}
			}
			if (dataType.equalsIgnoreCase("invalid")) {
				if (targetPage == true) {
					myAcctPage.clickOnLogOut();
					Assert.fail();
				} else {
					Assert.assertTrue(true);
				}
			}
		} catch (Exception e) {
			Assert.fail("An execption occured: " + e.getMessage());
		} finally {
			logger.info("*** Fiinished Login DDT ***");
		}

	}

}
