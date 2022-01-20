package scorm.everfi.tests.phd.smoke;

import org.testng.annotations.Test;

import scorm.everfi.base.TestBase;
import scorm.everfi.pages.phd.smoke.SignInWithGooglePage;

public class SigninWithGoogle extends TestBase{
	
	
	@Test
	public static void login() throws InterruptedException {
		SignInWithGooglePage.google_login();
	}
}
