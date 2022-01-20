package scorm.everfi.utils;

import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.Field;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.Issue.FluentCreate;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;

public class JiraServiceProvider {


	     private JiraClient Jira;

	     private String project;

	     private String JiraUrl;

	     public JiraServiceProvider(String JiraUrl, String username, String password, String project) {

	         this.JiraUrl=JiraUrl;

	         // create basic authentication object

	         BasicCredentials creds = new BasicCredentials(username, password);

	         // initialize the Jira client with the url and the credentials
	         Jira = new JiraClient(JiraUrl, creds);

	         this.project = project;

	     }
	     
	     public String  CreateJiraIssue(String issueType, String summary, String description, String reporterName ) {
	    	
	    	 String issue = "";
	    	 try {
	    		 //Issue.SearchResult sr =  Jira.searchIssues("summary ~ \""+summary+"\"");
	    		 //if(sr.total!=0) {
	    			// System.out.println("Same Issue Already Exists on Jira");
	    			
	    		 //}
	    		// else {
	    		 FluentCreate fluentCreate = Jira.createIssue(project, issueType);
	    		 fluentCreate.field(Field.SUMMARY, summary);
	    		 fluentCreate.field(Field.DESCRIPTION, description);
	    		 Issue newIssue = fluentCreate.execute();
	    		 
	    		 System.out.println("********************************************");

	    		 System.out.println("New Issue Created in Jira with ID: " + newIssue);

	    		 System.out.println("*******************************************");
	   
	    		 issue = newIssue.toString();
	    		// }

	    	 }
	    	 catch(JiraException e){
	    		 e.printStackTrace();
	    	 }
	
	    	 return issue;

	     }
}
