package scorm.everfi.tests.phd.smoke;


import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import scorm.everfi.base.TestBase;
import scorm.everfi.pages.phd.smoke.CourseLaunchPage;
import scorm.everfi.utils.ExtentTestManager;
import scorm.everfi.utils.JiraCreateIssue;

public class CourseLaunchTest extends TestBase{
	
	@JiraCreateIssue(isCreateIssue=true)
	@Test(description = "Verfiy if test launces in a new window")
	public void verCourseLaunchEmbedded() throws Exception {
		
		ExtentTestManager.getTest().log(Status.INFO, "Verify Course Launch in embedded mode started");
		CourseLaunchPage.launchCourseEmbedded();
		ExtentTestManager.getTest().log(Status.INFO, "Verify Course Launch in embedded mode Ended");
	}
	
	@JiraCreateIssue(isCreateIssue=true)
	@Test(description = "Verfiy if test launces in a new window")
	public void verCourseLaunchnonEmbedded() throws Exception {
		ExtentTestManager.getTest().log(Status.INFO, "Verify Course Launch in embedded mode started");
		CourseLaunchPage.launchCoursenonEmbedded();
		ExtentTestManager.getTest().log(Status.INFO, "Verify Course Launch in embedded mode Ended");
	}

}
