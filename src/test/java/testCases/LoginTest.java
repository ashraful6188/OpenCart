package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class LoginTest extends BaseClass {
	
	@Test(groups= {"Regression", "Master"})
	public void validateLogin() {
		logger.info("*** Stating Login Test ***");
		logger.debug("*** capturing LoginTest debug logs ***");
		try {
			// create a object of HomePage
			HomePage homePage = new HomePage(driver);
			homePage.clickOnMyAccount();
			homePage.clickOnLogin();
			logger.info("*** Cliked on login button ***");
			
			// loginPage
			LoginPage loginPage = new LoginPage(driver);
			logger.info("*** Adding credtial ***");
			loginPage.setEmail(prop.getProperty("email"));
			loginPage.setPassword(prop.getProperty("password"));
			loginPage.clickLogin();

			logger.info("*** Cliked on login button ***");

			// My account
			MyAccountPage myAcctPage = new MyAccountPage(driver);
			boolean displayStatusMyAcctPage = myAcctPage.isMyAccountPageExists();
			Assert.assertEquals(displayStatusMyAcctPage, true, "Login failed");
		} catch (Exception e) {
			Assert.fail();
		} finally {
			logger.info("*** LoginTest test completed ***");
		}

	}

}
