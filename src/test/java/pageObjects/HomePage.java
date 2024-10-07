package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {
	

	// constructor
	public HomePage(WebDriver driver) {
		super(driver);
	}

	// locators
	@FindBy(xpath = "//ul[@class='list-inline']//li[@class='dropdown']")
	WebElement link_myAccount;

	@FindBy(xpath = "//a[normalize-space()='Register']")
	WebElement link_register;
    @FindBy(xpath="//a[normalize-space()='Login']")
     WebElement link_login;
     
	// Action Method
	public void clickOnMyAccount() {
		link_myAccount.click();
	}

	public void clickOnRegister() {
		link_register.click();
	}
	
	public void clickOnLogin() {
		link_login.click();
	}

}