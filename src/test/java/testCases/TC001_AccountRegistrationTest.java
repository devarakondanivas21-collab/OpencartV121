package testCases;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.Homepage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {

	@Test(groups= {"Regression","Master"})
	public void verify_account_registration() {
		logger.info(" ******** Started TC001_AccountRegistrationTest******* ");
		
		Homepage hp = new Homepage(driver);
		hp.clickMyAccount();
		logger.info("Clicked on MyAccount Info...");
		
		hp.clickRegister();
		logger.info("Clicked on Register link.....");
		
		AccountRegistrationPage regpage = new AccountRegistrationPage(driver);
		logger.info("Providing Customer Details.....");
		
		regpage.setFirstName(randomString().toUpperCase());
		regpage.setLastName(randomString().toUpperCase());
		regpage.setEmail(randomString() + "@gmail.com"); // randomly genarate the Email
		regpage.setTelephone(randomnumber());

		// String password =randomAlphaNumeric();
		String password = randomAlphaNumeric();

		regpage.setPassword(password);
		regpage.setConfirmPassword(password);

		regpage.setPrivacyPolicy();
		regpage.clickContinue();
		
		logger.info("Validating Expected Message...");
		String confmsg = regpage.getConfirmationMsg();
		Assert.assertEquals(confmsg.trim(), "Your Account Has Been Created!");
	}

	public String randomString() {
		String generatedstring = RandomStringUtils.randomAlphabetic(5);
		return generatedstring;
	}

	public String randomnumber() {
		String generatednumber = RandomStringUtils.randomNumeric(10);
		return generatednumber;
	}

	public String randomAlphaNumeric() {
		String generatedstring = RandomStringUtils.randomAlphabetic(3);
		String generatednumber = RandomStringUtils.randomNumeric(3);
		return (generatednumber + "@" + generatedstring);
	}

}
