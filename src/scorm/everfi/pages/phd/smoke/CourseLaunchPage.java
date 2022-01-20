package scorm.everfi.pages.phd.smoke;

import org.openqa.selenium.support.PageFactory;
import scorm.everfi.utils.CommonUtils;


public class CourseLaunchPage extends CommonUtils{
	
	public static final CourseLaunchMethodsPage clm = PageFactory.initElements(driver,
			CourseLaunchMethodsPage.class);
	
	public static final String courseName = "Preventing Harassment and Discrimination";
	public static final String failcourseName = "Preventing Harassment and Discrimination:";
	
	public static void launchCourseEmbedded() throws Exception {
		
		implicitewait();
		CourseLaunchMethodsPage.launchCourse("Preventing Harassment and Discrimination: Supervisors",courseName);
		
	}
	
	public static void launchCoursenonEmbedded() throws Exception {
		implicitewait();
		CourseLaunchMethodsPage.launchCourseNonEmbedded("Preventing Harassment and Discrimination: Supervisors",failcourseName);
	}
	

}
