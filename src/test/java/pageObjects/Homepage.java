package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Homepage extends BasePage {

	public Homepage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//a[@title='My Account']")
	WebElement lnkMyAccElement;

	@FindBy(linkText = "Register")
	WebElement lnkAccElement;
	
	@FindBy(linkText = "Login")
	WebElement lnkLogin;

	public void clickMyAccount() {
		lnkMyAccElement.click();
	}

	public void clickRegister() {
		lnkAccElement.click();
	}
	
	public void clickLogin() {
		lnkLogin.click(); 
	}

}
