package TestClasses;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;

import Base.TestBase;

public class LogInToGoogle  extends TestBase{
	
	//@Parameters({"ID","FirstName","LastName"})
	
	@Test(dataProvider = "getParameters", alwaysRun=true)
	public void loginGoogle(Map<String, String> params){
		//FirefoxDriver driver = new FirefoxDriver();
		
		System.out.println("loginGoogle called with below Parameters:");
		
		System.out.println("FirstName: "+params.get("FirstName"));
		System.out.println("LastName: "+params.get("LastName"));
		
		System.out.println("InternalStepID: "+params.get("InternalStepID"));
	}

	/*@Test(dataProvider = "getParameters")
	public void logoutFromGoogle(){
		//FirefoxDriver driver = new FirefoxDriver();
		
		System.out.println("logoutFromGoogle called.");
	}*/
	
	
}
