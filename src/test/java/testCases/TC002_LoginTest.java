package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.Homepage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {

	@Test (groups={"Sanity" , "Master"})
	public void verifyLogin() {
		logger.info("****Starting TC002_LoginTest******");

		try {
			// Homepage
			Homepage hp = new Homepage(driver);
			hp.clickMyAccount();
			hp.clickLogin();

			// LoginPage
			LoginPage lp = new LoginPage(driver);
			lp.setEmail(p.getProperty("email"));
			lp.setPassword(p.getProperty("password"));
			lp.clickLogin();

			// MyAccount
			MyAccountPage macc = new MyAccountPage(driver);
			boolean targetPage = macc.isMyAccountPageExists();

			Assert.assertTrue(targetPage); // Assert.assertEquals(targetPage, true,"login Failed");
		} 
		catch (Exception e)
		{
			Assert.fail("Test Failed: " + e.getMessage());
		}
		logger.info("****Finished TC002_LoginTest******");

	}

}
