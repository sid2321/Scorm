package scorm.everfi.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.jboss.aerogear.security.otp.Totp;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import scorm.everfi.base.TestBase;

public class CommonUtils extends TestBase{
	
	protected static final String EXPLICIT = config
			.getProperty("ExplicitTimeout");
	protected static final String IMPLICIT = config
			.getProperty("implicitTimeout");
	protected static final String LOADING = config
			.getProperty("LoadingTimeout");
	
	@FindBy(id = "email")
	protected static WebElement scromEmail;
	
	@FindBy(id = "password")
	protected static WebElement scromPassword;
	
	@FindBy(css = "input[value='Submit']")
	protected static WebElement scromSubmit;
	
	@FindBy(xpath="//div[@title= 'Launch this course in the Sandbox']")
	protected static WebElement courseLaunchMsg;
	
	public static void init() throws InterruptedException {
		PageFactory.initElements(driver, CommonUtils.class);
	}
	
	public static void openBrowser(String browser) throws IOException {

		if (browser.equalsIgnoreCase("chrome")) {

			ChromeOptions options = new ChromeOptions();
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("profile.default_content_settings.popups", 0);
			options.addArguments("disable-extensions");
			prefs.put("credentials_enable_service", false);
			prefs.put("password_manager_enabled", false);
			options.setExperimentalOption("excludeSwitches", new String[] {"enable-automation"});
			options.setExperimentalOption("prefs", prefs);
			options.addArguments("chrome.switches", "--disable-extensions");
			options.addArguments("--test-type");
			options.addArguments("disable-infobars");
			options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
			options.setExperimentalOption("useAutomationExtension", false);
			ChromeOptions cap = new ChromeOptions();
			cap.setCapability(ChromeOptions.CAPABILITY, options);
			cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,
					UnexpectedAlertBehaviour.ACCEPT);
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir")
							+ "//drivers//chromedriver");
			driver = new ChromeDriver(cap);
			driver.manage().window().maximize();

		} else if (browser.equalsIgnoreCase("firefox")) {
			
			

		} else if (browser.equalsIgnoreCase("IE")) {

			
		}

		driver.manage().timeouts()
				.implicitlyWait(Integer.parseInt(IMPLICIT), TimeUnit.SECONDS);

	}
	
	public static String generate_otp() throws InterruptedException {
		implicitewait();
		init();
		Totp totp = new Totp("tegx y47c 44qw vtsb pbo2 j3cm g73u pby4");
	    String twoFactorCode = totp.now(); //Generated 2FA code here
	    return twoFactorCode;
		
	}
	
	/*
	 * Method: Login to Scorm Cloud.
	 * */
	public static void loginToScorm() throws InterruptedException {
		
		implicitewait();
		init();
		navigateToURL("https://cloud.scorm.com/sc/guest/SignInForm");
		write(scromEmail,"sgoankar@everfi.com");
		write(scromPassword,"9Siddhant@23211523");
		click(scromSubmit);
	}

	/*
	 * Method : click Description : click on an element(button)
	 */
	public static void click(final WebElement elem) throws InterruptedException {

		try {
			int time = Integer.parseInt(EXPLICIT);
			new WebDriverWait(driver, time).ignoring(WebDriverException.class,
					StaleElementReferenceException.class).until(
					new ExpectedCondition<Boolean>() {
						public Boolean apply(WebDriver driver) {
							elem.click();
							return true;
						}
					});

		} catch (NullPointerException e) {
			//log.error("NullPointerException", e);
		}
	}
	
	/*
	 * Method : staleElementAvoid : Avoids StaleElementReference Exception
	 */
	
	public static void staleElementAvoid(WebElement a)
	{
		expWait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(a)));
	}
	

	/*
	 * Method : write Description : write text(data) to a webElement(textbox)
	 */
	public static void write(final WebElement element, final String data)
			throws InterruptedException {
		try {
			int time = Integer.parseInt(EXPLICIT);
			if (!data.isEmpty()) {
				new WebDriverWait(driver, time).ignoring(
						WebDriverException.class,
						StaleElementReferenceException.class).until(
						new ExpectedCondition<Boolean>() {
							public Boolean apply(WebDriver driver) {
								element.clear();
								element.sendKeys(data);
								return true;
							}
						});
			}

		} catch (NullPointerException e) {
			//log.error("NullPointerException ", e);
		}
	}

	/*
	 * Method : navigateToURL Description : load the URL in the browser
	 */
	public static void navigateToURL(String urlKey) throws InterruptedException {

		driver.get(urlKey);
	}

	/*
	 * 
	 *wait for webpage to load. 
	 */
	public static void waitforPageTitle(String title) {

		int time = Integer.parseInt(EXPLICIT);
		expWait = new WebDriverWait(driver, time);
		expWait.until(ExpectedConditions.titleIs(title));
	}

	/*
	 * Method : waitforelementclick Description : wait for the element to be
	 * clicked
	 */
	public static void waitforelementclick(WebElement we) {

		int time = Integer.parseInt(EXPLICIT);
		expWait = new WebDriverWait(driver, time);
		expWait.until(ExpectedConditions.elementToBeClickable(we));
	}

	/*
	 * Method : waitforvisible Description : wait for the element to be visible
	 */
	public static void waitforvisible(WebElement we) {

		int time = Integer.parseInt(EXPLICIT);
		expWait = new WebDriverWait(driver, time);
		expWait.until(ExpectedConditions.visibilityOf(we));
	}

	/*
	 * Method : implicitewait Description : Set implicit wait between
	 * webelements
	 */
	public static void implicitewait() {

		int time = Integer.parseInt(IMPLICIT);
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}
	
	/*
	 * Method : gettitle Description : get the page title
	 */
	public static String gettitle() {

		return driver.getTitle();
	}

	/*
	 * 
	 * Method : Upload file
	 */
	public static void uploadFile(WebElement we, final String filename, WebElement bt) throws InterruptedException {
		write(we,filename);
		click(bt);
		waitforvisible(courseLaunchMsg);
		
	}
	
	// --------------------------------------------
	/**
	 * returns the number of rows present in the table
	 * 
	 * @param xpath
	 *            xpath of that table (string)
	 * @return
	 */
	public static int getTableSize(String xpath) {
		int size = 0;
		if (find("xpath", xpath)) {
			size = driver.findElements(By.xpath(xpath)).size();
		}
		System.out.println("Size of the table (number of rows in table) - " + size);

		return size;
	}
	
	
	// -----------------------------------------------------------------------------------------------------
		/**
		 * Method to check if a webelement is present or not
		 * 
		 * @param choice
		 *            xpath or id
		 * @param path
		 *            xpath string or id string
		 * @return true if webElement present else false
		 */
		public static boolean find(String choice, String path) {
			if (choice.equalsIgnoreCase("ID")) {
				try {
					driver.findElement(By.id(path));
				} catch (Exception e) {
					System.out.println("WebElement with ID :'" + path + "' not found");
					return false;
				}
			} else if (choice.equalsIgnoreCase("XPATH")) {
				try {
					driver.findElement(By.xpath(path));
				} catch (Exception e) {
					System.out.println("WebElement with xpath :'" + path + "' not found");
					return false;
				}
			} else {
				System.out.println("Wrong choice - choice must be either id or xpath");
				return false;
			}

			System.out.println(" Element found");
			return true;
		}
		
		
		
		
		public static JSONObject readJson(String fileName) {
			 JSONParser jsonParser = new JSONParser();
			 JSONObject jsonObject = new JSONObject();
		        try (FileReader reader = new FileReader(fileName))
		        {
		            //Read JSON file
		            jsonObject = (JSONObject) jsonParser.parse(reader);
		            System.out.println(jsonObject);
		    		//return contentArray;
		            //Iterate over employee array
		            //employeeList.forEach( emp -> parseEmployeeObject( (JSONObject) emp ) );
		 
		        } catch (FileNotFoundException e) {
		            e.printStackTrace();
		        } catch (IOException e) {
		            e.printStackTrace();
		        } catch (ParseException e) {
		            e.printStackTrace();
		        }
				return jsonObject;
		}
		
		
		public static void selectCourse(String courseName) {
			
			int time = Integer.parseInt(EXPLICIT);
			expWait = new WebDriverWait(driver, time);
			expWait.until(ExpectedConditions.elementToBeClickable((By.xpath("//table[@id='course-table']/tbody"
					+ "/tr/td/a[text()='"+courseName.trim()+"']"))));
			driver.findElement(By.xpath("//table[@id='course-table']/tbody"
					+ "/tr/td/a[text()='"+courseName.trim()+"']")).click();
		}
		
		public static void switchWindow() {

			Set<String> allWindowHandles = driver.getWindowHandles();
			for (String currentWindowHandle : allWindowHandles) {

				driver.switchTo().window(currentWindowHandle);
			}
	        System.out.println("Heading of child window is " + driver.getTitle());
	        driver.switchTo().frame("content-frame");
	        System.out.println("Heading of frame is" + driver.getTitle());
	        driver.switchTo().frame(0);
	        System.out.println("Heading of frame o is " + driver.getTitle());
		}
		
		
		public static void switchCourseFrame() {
			
	        System.out.println("Heading of child window is " + driver.getTitle());
	        driver.switchTo().frame("ScormContent");
	        System.out.println("Heading of frame is" + driver.getTitle());
	        driver.switchTo().frame(0);
	        System.out.println("Heading of frame is " + driver.getTitle());
	        driver.switchTo().frame("course-iframe");
	        System.out.println("Heading of frame is " + driver.getTitle());
		}
		
		public static void frameVisible(WebElement courseProperties) {

			int time = Integer.parseInt(EXPLICIT);
			expWait = new WebDriverWait(driver, time);
			expWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(courseProperties));
			
		}
		
		/*
		 * Method : isElementPresent Description : Select value from dropdown
		 * displayed
		 */
		public static void newDropDownOptionSelect(WebElement eleDropddown,
				String optionname) throws InterruptedException {
			
			Select objSelect = new Select(eleDropddown);
			List <WebElement> elementCount = objSelect.getOptions();
			int optCount = elementCount.size();
			System.out.println("Options Count : " + optCount);
			for (int i = 0; i < optCount; i++) {
				String optName = elementCount.get(i).getAttribute("value").trim();
				System.out.println("Option Name : " + optName);
				if (optName.equalsIgnoreCase(optionname)) {
					click(elementCount.get(i));
					Thread.sleep(1000);
					System.out.println("Entered option Selected");
					break;
				}
			}
		}

		@SuppressWarnings("deprecation")
		public static void waitforCourseLaunch() {
			
			 FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				        .withTimeout(30, TimeUnit.SECONDS)
				        .pollingEvery(5, TimeUnit.SECONDS)
				        .ignoring(NoSuchElementException.class);
			 
			 wait.until(
					    ExpectedConditions.visibilityOfElementLocated(By.id("ScormContent")));

		}
		

		/*
		 * Method : isElementPresent Description : Verify that a webelement is
		 * displayed
		 */
		public static boolean isElementPresent(WebElement elem) {

			if (elem.isDisplayed()) {
				return true;
			}
			return false;
		}

		/*
		 * Method : checkCheckBox Description : Select a check Box located by the
		 * webelement
		 */
		public static void checkCheckBox(WebElement elem)
				throws InterruptedException {

			if (!elem.isSelected()) {
				click(elem);
			}
		}

		/*
		 * Method : uncheckCheckBox Description : unselect a check Box located by
		 * the webelement
		 */
		public static void uncheckCheckBox(WebElement elem)
				throws InterruptedException {

			if (elem.isSelected()) {
				click(elem);
			}
		}

		/*
		 * Method : waits Description : wait for 3 sec
		 */
		public static void waits() throws InterruptedException {

			Thread.sleep(3000);
		}
		
		/*
		 * *\Method : Switch to main frame
		 */
		public static void defaultFrame() {
			driver.switchTo().defaultContent();
		}
		
		
		/* Method to retrieve text */
		public static String getText(final WebElement elem) {

			int time = Integer.parseInt(EXPLICIT);
			new WebDriverWait(driver, time).ignoring(
					StaleElementReferenceException.class).until(
					new ExpectedCondition<Boolean>() {
						String text = null;

						public Boolean apply(WebDriver driver) {
							text = elem.getText();
							return true;
						}
					});

			return elem.getText();

		}
}		
		     
