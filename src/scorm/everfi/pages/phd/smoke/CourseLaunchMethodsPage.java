package scorm.everfi.pages.phd.smoke;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import scorm.everfi.utils.CommonUtils;
import scorm.everfi.utils.ExtentTestManager;

public class CourseLaunchMethodsPage extends CommonUtils{
	

	@FindBy(partialLinkText = "Go to Course Library")
	static WebElement btnCourseLibrary;
	
	@FindBy(xpath="//div[@title= 'Launch this course in the Sandbox']")
	static WebElement launchCourse;
	
	@FindBy(xpath = "//iframe[contains(@src,'cloud.scorm.com')]")
	static WebElement courseProperties;
	
	@FindBy(id = "pe_TabLaunchBehavior")
	static WebElement launchBehaviour;
	
	@FindBy(id = "pe_ddlScoLaunchType")
	static WebElement launchType;
	
	@FindBy(id ="pe_rdoFullScreen")
	static WebElement fullScreenLaunch;
	
	@FindBy(id = "pe_btnSave")
	static WebElement btnSave;
	
	public CourseLaunchMethodsPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	public static void launchCourse(String course,String courseName) throws Exception{
		
		ExtentTestManager.getTest().log(Status.INFO, "Clicked on Go to Library");
		selectCourse(course);
		waitforvisible(launchCourse);
		click(launchCourse);
		Thread.sleep(10000); 
		ExtentTestManager.getTest().log(Status.INFO, "Clicked on Launch");
		switchWindow();
		String name = driver.findElement(By.xpath("//h1")).getText();
		Assert.assertEquals(courseName.trim(),name.trim());
	}
	
	public static void launchCourseNonEmbedded(String course,String courseName) throws InterruptedException {
		
		ExtentTestManager.getTest().log(Status.INFO, "Clicked on Go to Library");
		selectCourse(course);
		waitforvisible(launchCourse);
		frameVisible(courseProperties);
		click(launchBehaviour);
		newDropDownOptionSelect(launchType,"frameset");
		checkCheckBox(fullScreenLaunch);
		click(btnSave);
		ExtentTestManager.getTest().log(Status.INFO, "Frameset ready to launch in embedded mode");
		defaultFrame();
		click(launchCourse);
		Thread.sleep(10000); 
		ExtentTestManager.getTest().log(Status.INFO, "Clicked on Launch");
		switchCourseFrame();
		String name = driver.findElement(By.xpath("//h1")).getText();
		Assert.assertEquals(courseName.trim(),name.trim());
	}
	
	
	
	
}
