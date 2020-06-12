package Base.Driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import Base.Base;
import PageClasses.Utilities.ConfigDetails;

public class Driver extends Base{

	private static Driver driverObj;
	
	
	private static String sBrowserType = "";
	
	static {
		sBrowserType = ConfigDetails.getProperty("browser");
	}
	
	private Driver(){
		
	}
	
	public static WebDriver getWebDriver(){
		
		if (driverObj == null){
			driverObj = new Driver();
			
			switch (sBrowserType.toLowerCase()){
			
			case "chrome":
				driver = new ChromeDriver();
				break;
			}
		}
		
		return driver;
	}
	
	
	
}
