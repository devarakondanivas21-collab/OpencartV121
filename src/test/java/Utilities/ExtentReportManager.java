package Utilities;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

	public class ExtentReportManager implements ITestListener {

	    public ExtentSparkReporter sparkReporter;
	    public ExtentReports extent;
	    public ExtentTest test;
	    
	    
	    String repName;
	    
	    public void onStart(ITestContext testContext) {
	    	
	        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
	        repName = "Test-Report-" + timeStamp + ".html";

	        // Specify location for the report
	        sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName); 
	        
	        // Set report configurations
	        sparkReporter.config().setDocumentTitle("OpenCart Automation Report");
	        sparkReporter.config().setReportName("OpenCart Functional Testing");
	        sparkReporter.config().setTheme(Theme.DARK);

	        // Initialize ExtentReports and attach the reporter
	        extent = new ExtentReports();
	        extent.attachReporter(sparkReporter);

	        // Add system information to the report
	        extent.setSystemInfo("Application", "OpenCart");
	        extent.setSystemInfo("Module", "Admin");
	        extent.setSystemInfo("Sub Module", "Customers");
	        extent.setSystemInfo("User Name", System.getProperty("user.name"));
	        extent.setSystemInfo("Environment", "QA");

	        // Get additional information from test context
	        String os = testContext.getCurrentXmlTest().getParameter("os");
	        extent.setSystemInfo("Operating System", os);

	        String browser = testContext.getCurrentXmlTest().getParameter("browser");
	        extent.setSystemInfo("Browser", browser);

	        // Add groups to the report if available
	        List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
	        if (!includedGroups.isEmpty()) {
	            extent.setSystemInfo("Groups", includedGroups.toString());
	        }
	    }
	    
	    public void onTestSuccess(ITestResult result) {
	        // Log success if the test passed
	    	test = extent.createTest(result.getTestClass().getName());
	    	test.assignCategory(result.getMethod().getGroups());
	        test.log(Status.PASS, result.getName() + " got successfully executed");
	    }

	    
	    public void onTestFailure(ITestResult result) {
	        // Log failure details
	    	test=extent.createTest(result.getClass().getName());
	    	test.assignCategory(result.getMethod().getGroups());
	    	
	        test.log(Status.FAIL, result.getName() + " got failed");
	        test.log(Status.INFO, result.getThrowable().getMessage());

	        // Capture screenshot if required and add to the report
	        try {
	            String imgPath = new BaseClass().captureScreen(result.getName()); // Capture screenshot
	            test.addScreenCaptureFromPath(imgPath);
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    public void onTestSkipped(ITestResult result) {
	    	
	    	test=extent.createTest(result.getClass().getName());
	    	test.assignCategory(result.getMethod().getGroups());
	    	
	        // Log skipped test details
	        test.log(Status.SKIP, result.getName() + " got skipped");
	        test.log(Status.INFO, result.getThrowable().getMessage());
	    }

	    public void onFinish(ITestContext testContext) {
	        // Finalize the report after test execution
	        extent.flush();

	        // Open the report in the default browser
	        String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;
	        File extentReport = new File(pathOfExtentReport);
	        
	        try {
	            Desktop.getDesktop().browse(extentReport.toURI());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	       /* try {
	        	URL url =new URL("file://"+System.getProperty("user.dir")+"\\reports\\"+repName);
	        	
	        	//Creating the email message
	        	ImageHtmlEmail email = new ImageHtmlEmail();
	        	email.setDataSourceResolver(new DataSourceUrlResolver(url));
	        	email.setHostName("smtp.googlemail.com");
	        	email.setSmtpPort(465);
	        	email.setAuthenticator(new DefaultAuthenticator)("devarakondanivas084@gmail.com","Nivas@939039"));
	        	email.setSSLOnConnect(true);
	        	email.setFrom("devarakondanivas084@gmail.com"); //Sender
	        	email.setSubject("Test Results");
	        	email.setMsg("Please find Attached Report....");
	        	email.addTo("devarakondanivas21@gmail.com"); //Receiver
	        	email.attach(url,"extent report","please check report...." );
	        	email.send();
	            }
	        catch (Exception e)
	        {
	        	e.printStackTrace();
	        }*/
	                 
	        
	    }
	}


