package Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import Base.PageBase;

public class HomePage extends PageBase{

	@FindBy (id="welcome")
	WebElement lblWelcome;
	
	/*@FindBy(how = How.ID, using = "welcome")
	private WebElement username;*/
	
	
	public HomePage(){
		PageFactory.initElements(driver, this);
	}
	
	public boolean validateLogin(){
		boolean bLogin = false;
		testNode = test.createNode("Validate Login");
		testNode.info("Inside Home Page class.");
		
		if(lblWelcome.isDisplayed()){
			bLogin = true;
			testNode.info("Welcome label is displayed on landing page");
			testNode.pass("Login successful");
			Assert.assertTrue(true);
		}else{
			testNode.pass("Login failed");
			Assert.assertTrue(false);
		}
		
		return bLogin;
	}
	
	
	public String getLoggedInUserName(){
		
		return lblWelcome.getText().split(" ")[1];
	}
}
