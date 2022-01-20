package scorm.everfi.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import scorm.everfi.utils.ExtentTestManager;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import scorm.everfi.base.TestBase;

public class TestNGTestListener extends TestBase implements ITestListener{
	
	public final String JiraUrl = "https://test-siddhant.atlassian.net";
	public final String JiraUsername = "gaonkersiddhant@gmail.com";
	public final String JiraToken = "CvG4sXldWpEht7txySSC2338";
	public final String JiraProject = "TEST";
	
	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		System.out.println(("*** Running test method " + result.getMethod().getDescription() + "..."));
		ExtentTestManager.startTest(result.getMethod().getMethodName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		System.out.println("*** Executed " + result.getMethod().getDescription() + " test successfully...");
		ExtentTestManager.getTest().log(Status.PASS, result.getMethod().getMethodName()+ " passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		
		String newIssue = "";
		JiraCreateIssue issue = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(JiraCreateIssue.class);
		 boolean isJiraIssue = issue.isCreateIssue();
		 if(isJiraIssue) {

			 JiraServiceProvider JiraServiceProvider = new JiraServiceProvider(JiraUrl,
					 JiraUsername, JiraToken, JiraProject);
	         String issueDescription = "Failure Reason from Automation Testing\n\n" + result.getThrowable().getMessage()

	                    + "\n";

	         issueDescription.concat(ExceptionUtils.getFullStackTrace(result.getThrowable()));




	         String issueSummary = result.getMethod().getConstructorOrMethod().getMethod().getName()

	                    + " Failed in Automation Testing";

	            

	         newIssue =  JiraServiceProvider.CreateJiraIssue("Bug", issueSummary, issueDescription, "Automated Testing");
		 }
		
		// Below code is for Generating Extent Reports
		// TODO Auto-generated method stub
		ExtentTestManager.getTest().log(Status.INFO, "*** Test execution " + result.getMethod().getMethodName() + " failed...");
		ExtentTestManager.getTest().log(Status.INFO,(result.getMethod().getMethodName() +  "failed!"));
		ExtentTestManager.getTest().log(Status.INFO, "New issue created with issue ID: " + newIssue);

		String targetLocation = null;

		String testClassName = result.getMethod()
				.getTestClass()
				.getName().trim();
		LocalDateTime timeStamp =LocalDateTime.now(); // get timestamp
		String testMethodName = result.getName().toString().trim();
		String screenShotName = testMethodName + timeStamp + ".png";
		String fileSeperator = System.getProperty("file.separator");
		String reportsPath = System.getProperty("user.dir") + fileSeperator + "TestReport" + fileSeperator
				+ "screenshots";
		ExtentTestManager.getTest().log(Status.INFO,"Screen shots reports path - " + reportsPath);
		try {
			File file = new File(reportsPath + fileSeperator + testClassName); // Set
																				// screenshots
																				// folder
			if (!file.exists()) {
				if (file.mkdirs()) {
					ExtentTestManager.getTest().log(Status.INFO,"Directory: " + file.getAbsolutePath() + " is created!");
				} else {
					ExtentTestManager.getTest().log(Status.INFO,"Failed to create directory: " + file.getAbsolutePath());
				}

			}

			File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			targetLocation = reportsPath + fileSeperator + testClassName + fileSeperator + screenShotName;// define
																											// location
			File targetFile = new File(targetLocation);
			ExtentTestManager.getTest().log(Status.INFO,"Screen shot file location - " + screenshotFile.getAbsolutePath());
			ExtentTestManager.getTest().log(Status.INFO,"Target File location - " + targetFile.getAbsolutePath());
			FileHandler.copy(screenshotFile, targetFile);

		} catch (FileNotFoundException e) {
			ExtentTestManager.getTest().log(Status.INFO,"File not found exception occurred while taking screenshot " + e.getMessage());
		} catch (Exception e) {
			ExtentTestManager.getTest().log(Status.INFO,"An exception occurred while taking screenshot " + e.getCause());
		}

		// attach screenshots to report
		try {
			ExtentTestManager.getTest().fail("Screenshot",
					MediaEntityBuilder.createScreenCaptureFromPath(targetLocation).build());
		} catch (IOException e) {
			ExtentTestManager.getTest().log(Status.INFO,"An exception occured while taking screenshot " + e.getCause());
		}
		ExtentTestManager.getTest().log(Status.FAIL, "Test Failed");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		System.out.println("*** Test " + result.getMethod().getMethodName() + " skipped...");
		ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		System.out.println("*** Test failed but within percentage % " + result.getMethod().getMethodName());
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		System.out.println("*** Test failed due to timeout % " + result.getMethod().getMethodName());
		ExtentTestManager.getTest().log(Status.FAIL, "Test failed with timeout");
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		System.out.println("*** Test Suite " + context.getName() + " started ***");
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		System.out.println(("*** Test Suite " + context.getName() + " ending ***"));
		ExtentTestManager.endTest();
		ExtentManager.getInstance().flush();
	}

	
}
