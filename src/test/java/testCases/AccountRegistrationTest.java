package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.RegistrationPage;
import testBase.BaseClass;

public class AccountRegistrationTest extends BaseClass {
	
	
	@Test(groups= {"Sanity", "Master"})
	public void validateAccountRegistration() {
        logger.info("*******Stating Account Registration Test ********");
        logger.debug("######### This is a debug log massage ######");
        
        try {
        	
		// create an object of HomePage
		HomePage homePage = new HomePage(driver);
		logger.info("*** clickin on my account ***");
		homePage.clickOnMyAccount();
		logger.info("*** clickin on Register ***");
		homePage.clickOnRegister();
		
		// create an object of RegistrationPage
		RegistrationPage regPage = new RegistrationPage(driver);
		logger.info("***Providing customer information***");
		regPage.setFirstName(randomString().toUpperCase());
		regPage.setLastName(randomString().toUpperCase());
		regPage.setEmail(randomString() + "@mail.com");
		regPage.setTelephone(randomNumber());
		String pwd = randomAlphaNumeric();
		regPage.setPassword(pwd);
		regPage.setConfirmPassword(pwd);

		regPage.setPrivacyPolicy();
		regPage.clickContinue();
		logger.info("Validating expected Massage");
		regPage.getConFirmationMsg();
		String config = regPage.getConFirmationMsg();
		Assert.assertEquals(config, "Your Account Has Been Created!");
		logger.info("Account Registration test passed");
	}catch(Exception e) {
		
		e.getMessage();
	}
        finally {
        	logger.info("***** Finished account registration testing *****");
        }
        
	}
}
