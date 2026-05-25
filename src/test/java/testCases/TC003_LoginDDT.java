package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import Utilities.DataProviders;
import pageObjects.Homepage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

/*
Data is valid - login Success - test pass --logout
Data is valid -- login fail -- test fail

data is invalid --login success --test fail --logout 
data is invalid --login fail -- test pass 
*/

public class TC003_LoginDDT extends BaseClass {
	@Test(	dataProvider = "LoginData",	dataProviderClass = DataProviders.class, groups = "Datadriven")
		public void verify_DDT(String email, String pwd, String exp)
		{
		  
	  	logger.info("***** Starting TC_003_LoginDDT *****");

		try {

			driver.get(p.getProperty("appUrl"));

			// Homepage
			Homepage hp = new Homepage(driver);
			hp.clickMyAccount();
			hp.clickLogin();

			// Login Page
			LoginPage lp = new LoginPage(driver);
			lp.setEmail(email);
			lp.setPassword(pwd);
			lp.clickLogin();

			// MyAccount Page
			MyAccountPage macc = new MyAccountPage(driver);

			boolean targetPage = macc.isMyAccountPageExists();

			if(exp.equalsIgnoreCase("Valid"))
			{
				if(targetPage)
				{
					macc.clickLogout();
					Assert.assertTrue(true);
				}
				else
				{
					Assert.fail();
				}
			}

			if(exp.equalsIgnoreCase("Invalid"))
			{
				if(targetPage)
				{
					macc.clickLogout();
					Assert.fail();
				}
				else
				{
					Assert.assertTrue(true);
				}
			}

		}
		catch(Exception e)
		{
			Assert.fail();
		}

		logger.info("***** Finished TC_003_LoginDDT *****");
	}
}
	