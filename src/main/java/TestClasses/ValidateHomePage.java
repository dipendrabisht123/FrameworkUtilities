package TestClasses;

import java.util.Map;

import org.testng.annotations.Test;

import Base.TestBase;

public class ValidateHomePage extends TestBase{
	
	@Test(dataProvider="getParameters")
	public void validatePageFields(Map<String, String> params){
		System.out.println("Home page fields validated successfully with below Parameters:");
		
		System.out.println("FirstName: "+params.get("FirstName"));
		System.out.println("LastName: "+params.get("LastName"));
		
		System.out.println("InternalStepID: "+params.get("InternalStepID"));
	}
}
