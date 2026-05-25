package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {
	
	
	public LoginPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		}
	@FindBy(xpath = "//input[@id='input-email']")
	WebElement txtEmailAddress;
	
	@FindBy(xpath="//input[@id='input-password']")
	WebElement txtPassword;
	
	@FindBy(xpath="//input[@value='Login']")
	WebElement btnLogin;
	
	@FindBy(xpath="//div[contains(@class,'alert-danger')]")
	WebElement msgWarning;
	
	public void setEmail(String Email) {
	txtEmailAddress.sendKeys(Email);
	}
	
	public void setPassword(String pwd) {
		txtPassword.sendKeys(pwd);
	}
	
	public void clickLogin() {
		btnLogin.click();
	}
	public boolean isWarningMessageDisplayed()
	{
		try
		{
			return msgWarning.isDisplayed();
		}
		catch(Exception e)
		{
			return false;
		}
	}
}