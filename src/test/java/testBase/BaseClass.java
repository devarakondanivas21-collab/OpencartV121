package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import org.openqa.selenium.remote.RemoteWebDriver;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

	public static WebDriver driver;
	public Logger logger;
	public Properties p;

	@BeforeClass(groups = { "Sanity", "Regression", "Master", "Datadriven" })

	@Parameters({ "os", "browser" })

	public void setup(String os, String br) throws IOException {

		// Loading config.properties file

		FileReader file = new FileReader("./src/test/resources/config.properties");

		p = new Properties();
		p.load(file);

		// Logger

		logger = LogManager.getLogger(this.getClass());

		// =========================
		// REMOTE EXECUTION
		// =========================

		if (p.getProperty("execution_env").equalsIgnoreCase("remote")) {

			switch (br.toLowerCase()) {

			case "chrome":

				ChromeOptions chromeOptions = new ChromeOptions();

				chromeOptions.addArguments("--remote-allow-origins=*");
				chromeOptions.addArguments("--disable-dev-shm-usage");
				chromeOptions.addArguments("--no-sandbox");

				driver = new RemoteWebDriver(
						new URL("http://localhost:4444"),
						chromeOptions);

				break;

			case "edge":

				EdgeOptions edgeOptions = new EdgeOptions();

				driver = new RemoteWebDriver(
						new URL("http://localhost:4444"),
						edgeOptions);

				break;

			case "firefox":

				FirefoxOptions firefoxOptions = new FirefoxOptions();

				driver = new RemoteWebDriver(
						new URL("http://localhost:4444"),
						firefoxOptions);

				break;

			default:
				System.out.println("Invalid browser name...");
				return;
			}
		}

		// =========================
		// LOCAL EXECUTION
		// =========================

		else if (p.getProperty("execution_env").equalsIgnoreCase("local")) {

			switch (br.toLowerCase()) {

			case "chrome":

				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();

				break;

			case "edge":

				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();

				break;

			case "firefox":

				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();

				break;

			default:

				System.out.println("Invalid browser name...");
				return;
			}
		}

		// =========================
		// COMMON SETTINGS
		// =========================

		driver.manage().deleteAllCookies();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.get(p.getProperty("appUrl"));

		driver.manage().window().maximize();

		System.out.println(driver);
		System.out.println(driver.getClass());
	}

	@AfterClass(groups = { "Sanity", "Regression", "Master", "Datadriven" })

	public void teardown() throws InterruptedException {

		Thread.sleep(3000);

		if (driver != null) {

			driver.quit();
		}
	}

	// =========================
	// Random String
	// =========================

	public String randomString() {

		return RandomStringUtils.randomAlphabetic(5);
	}

	// =========================
	// Random Number
	// =========================

	public String randomNumber() {

		return RandomStringUtils.randomNumeric(10);
	}

	// =========================
	// Random AlphaNumeric
	// =========================

	public String randomAlphaNumeric() {

		String generatedString = RandomStringUtils.randomAlphabetic(3);

		String generatedNumber = RandomStringUtils.randomNumeric(3);

		return generatedString + "@" + generatedNumber;
	}

	// =========================
	// Screenshot Capture
	// =========================

	public String captureScreen(String testName) throws IOException {

		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss")
				.format(new Date());

		TakesScreenshot ts = (TakesScreenshot) driver;

		File sourceFile = ts.getScreenshotAs(OutputType.FILE);

		String targetFilePath = System.getProperty("user.dir")
				+ "\\screenshots\\"
				+ testName + "_"
				+ timeStamp + ".png";

		File targetFile = new File(targetFilePath);

		FileUtils.copyFile(sourceFile, targetFile);

		return targetFilePath;
	}
}