package scorm.everfi.pages.phd.smoke;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import scorm.everfi.utils.CommonUtils;

public class SignInWithGooglePage extends CommonUtils{
	
	@FindBy(xpath = "//a[@class='google-oauth-button']")
	static WebElement googleBtn;
	
	@FindBy(xpath = "//input[@type='email']")
	static WebElement google_email;
	
	@FindBy(xpath = "//input[@type='password']")
	static WebElement google_password;
	
	@FindBy(xpath = "//input[@type='tel']")
	static WebElement totp;
	
	@FindBy(id="headingText")
	static WebElement headingText;
	
	@FindBy(xpath="//span[text()='Next']")
	static WebElement next;
	
	@FindBy(xpath="//span[text()='Try another way']")
	static WebElement try_totp;
	
	@FindBy(xpath="//div[text()='Get a verification code from the ']")
	static WebElement totp_verify;
	
	public static final SignInWithGooglePage clm = PageFactory.initElements(driver,
			SignInWithGooglePage.class);
	
	
	public static void google_login() throws InterruptedException {
		
		implicitewait();
		CommonUtils.navigateToURL("https://admin.fifoundry.net/en/admin/session/new");
		
		waitforvisible(googleBtn);
		click(googleBtn);
		write(google_email,"sgoankar");
		click(next);
		Thread.sleep(5000);
		write(google_password,"9Altroz@2321");
		click(next);
		Thread.sleep(5000);
		// OTP value is returned from getTwoFactor method
		click(try_totp);

		Thread.sleep(5000);
		click(totp_verify);
		Thread.sleep(5000);
		write(totp,generate_otp());
		click(next);
		Thread.sleep(50000);
		
	}

}
