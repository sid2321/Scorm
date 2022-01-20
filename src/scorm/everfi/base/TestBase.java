package scorm.everfi.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.google.gson.JsonArray;

import scorm.everfi.utils.CommonUtils;

public class TestBase {
	
	@FindBy(id="nav-btn")
	protected static WebElement addContent;
	
	@FindBy(xpath="//a[text()='Import a SCORM, AICC, xAPI or cmi5 package']")
	protected static WebElement importPackage;
	
	@FindBy(id="lib_importForm_fileToImport")
	protected static WebElement fileImport;
	
	@FindBy(id="startImportButton")
	protected static WebElement startImport;
	
	@FindBy(xpath = "//a[@href='/sc/user/Home']")
	protected static WebElement navHome;
	
	@FindBy(xpath = "(//a[@href='/sc/user/Library'])[1]")
	protected static WebElement navLibrary;
	
	@FindBy(id = "selectAll")
	protected static WebElement selectAllCourses;
	
	@FindBy(xpath = "//button[@title='Delete Courses']")
	protected static WebElement deletCourses;
	
	@FindBy(xpath = "//button[text()='Yes']")
	protected static WebElement deleteYes;
	
	@FindBy(xpath = "//div[@class='jumbotron']")
	protected static WebElement deleteCourseMsg;
	
	protected static Properties config;
	protected static WebDriver driver;
	protected static String browser;
	protected static WebDriverWait expWait;
	protected static final String PROJECTDIR = System.getProperty("user.dir");
	protected static final String uploadPath = PROJECTDIR + "//scorm_packages//";
	protected static final String coursePath = PROJECTDIR + "//data//courses.json";
	
	public static void init() throws InterruptedException {
		PageFactory.initElements(driver, TestBase.class);
	}
	
	/*
	 * Method: Prepares 
	 * 
	@SuppressWarnings("unchecked")
	@BeforeSuite
	public static void importCourses() throws IOException, InterruptedException {
		
		init();
		pageInitialisation();
		CommonUtils.openBrowser("chrome");
		CommonUtils.loginToScorm();
		CommonUtils.waitforPageTitle("SCORM Cloud - Log in securely to your account");
		JSONObject object = CommonUtils.readJson(coursePath);
		JSONArray array = (JSONArray) object.get("courses");
		
		Iterator<String> iterator = array.iterator();
		
	       List<String>
           list = getListFromIterator(iterator);
	       
	       
	       for (int i = 0; i < list.size(); i++) 
	       {
	    	   
	    	   System.out.print(list.get(i) + " ");  
	    		CommonUtils.waitforelementclick(addContent);
	    		CommonUtils.click(addContent);
	    		CommonUtils.click(importPackage);
	    		CommonUtils.uploadFile(fileImport,uploadPath + list.get(i) + ".zip" , startImport);
	    		CommonUtils.click(navHome);
	    		Thread.sleep(3000);
	       }    	
  
		teardown();
	}*/
	
	/*@BeforeTest
	public static void loginToScorm() throws IOException, InterruptedException {
		init();
		pageInitialisation();
		CommonUtils.openBrowser("chrome");
		CommonUtils.loginToScorm();
		CommonUtils.waitforPageTitle("SCORM Cloud - Log in securely to your account");
		CommonUtils.waitforvisible(navLibrary);
		CommonUtils.click(navLibrary);
		
	}*/
	
	@BeforeTest
	public static void openPage() throws InterruptedException, IOException {
		init();
		pageInitialisation();
		CommonUtils.openBrowser("chrome");
	}
	

	public static void pageInitialisation() throws IOException {

		config = new Properties();
		FileInputStream ip = new FileInputStream(new File(PROJECTDIR + "//config//config.properties"));
		config.load(ip);

	}
	
	/* 
	 * 
	 * close browser after test
	 * */
	@AfterTest
	public static void teardown() {
		driver.close();
		driver.quit();
	}

	@AfterSuite
	public static void deleteCourses() throws Exception {
		init();
		pageInitialisation();
		CommonUtils.openBrowser("chrome");
		CommonUtils.loginToScorm();
		CommonUtils.waitforPageTitle("SCORM Cloud - Log in securely to your account");
		CommonUtils.click(navLibrary);
		deleteCourse();
	}
	
    public static <T> List<T>
    getListFromIterator(Iterator<T> iterator)
    {
  
        // Create an empty list
        List<T> list = new ArrayList<>();
  
        // Add each element of iterator to the List
        iterator.forEachRemaining(list::add);
  
        // Return the List
        return list;
    }
	
    public static void deleteCourse() throws InterruptedException {
    	
    	int size = CommonUtils.getTableSize("//table[@id='course-table']/tbody/tr");
    	
    	if(size == 0) {
    		return;
    	}
    	
    	CommonUtils.click(selectAllCourses);
    	CommonUtils.click(deletCourses);
    	CommonUtils.click(deleteYes);
    	 try {
    		 Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
    				 .withTimeout(Duration.ofSeconds(30))
    			     .pollingEvery(Duration.ofSeconds(5))
    			     .ignoring(NoSuchElementException.class);
    		 wait.until(ExpectedConditions.visibilityOf(deleteCourseMsg));
    			
    	    } catch (Exception e) {
    	    	System.out.println("Delete Course Message Not Found");
    	    	deleteCourse();
    	    }finally {
    	    	teardown();
    	    }
    	 
    	 
    }
    
    
}
