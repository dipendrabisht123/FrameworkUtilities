package Base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Base {

	protected static WebDriver driver;
	protected static ExtentHtmlReporter htmlReporter;
	protected static ExtentReports report;	
	protected static ExtentTest test;
	protected static ExtentTest testNode;
	
	protected static Logger logger = LogManager.getLogger(Base.class.getSimpleName());
	
	
	
}
