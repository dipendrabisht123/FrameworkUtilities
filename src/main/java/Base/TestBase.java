package Base;

import static Base.Driver.Driver.getWebDriver;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.internal.annotations.ITest;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import PageClasses.Utilities.ConfigDetails;

public class TestBase extends Base{

	/*ExtentHtmlReporter htmlReporter;
	protected ExtentReports report;	
	protected ExtentTest test;*/
	/**
	 * Description: Data-provider to get data for  testNG.xml file and return through HashMap
	 * @param m :- reflection class Method object, which refers to the @Test method which is calling this data-provider
	 * @param ctx
	 * @return
	 * @throws Exception
	 */
	@DataProvider()
    public Object[][] getParameters(Method m, ITestContext ctx) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		
        for (XmlClass thisClass : ctx.getCurrentXmlTest().getXmlClasses()) {
        	
        	if (thisClass.getName().equals(this.getClass().getName())){
        		Map<String, String> classParams = new HashMap<String, String>();
        		classParams = thisClass.getAllParameters();
        		
        		if (classParams.size() > 0){
        			params.putAll(classParams);
        		}
        	
        		for (XmlInclude method : thisClass.getIncludedMethods()) {
                    if (method.getName().equals(m.getName())) {
                    	Map<String, String> methodParams = new HashMap<String, String>();
                    	
                    	methodParams = method.getAllParameters();
                    	
                    	if (methodParams.size() > 0){
                    		params.putAll(methodParams);
                    		break;
                    	}
                    	
                    }
                }
        	
        	}
            
        }
        return new Object[][] { { params } };
    }

	@BeforeSuite
	public void setupSuit(){
		htmlReporter = new ExtentHtmlReporter("./test-output/ExtentReport.html");
		
		htmlReporter.config().setDocumentTitle("ProjectReport");
		htmlReporter.config().setReportName("Regression Execution Report");
		htmlReporter.config().setTheme(Theme.DARK);
	}
	
	@BeforeTest
	public void setupTest(){
		
		report = new ExtentReports();
		report.attachReporter(htmlReporter);
		
		report.setSystemInfo("Operating System", "Windows 10");
		report.setSystemInfo("Browser", ConfigDetails.getProperty("browser"));
		report.setSystemInfo("Tester Name", "Dipendra");
		
	}
	
	@AfterTest
	public void cleanupTest(){
		report.flush();
		getWebDriver().close();
		getWebDriver().quit();
	}

	@AfterMethod
	public void afterMethod (ITestResult iTestResult){
		
		if (iTestResult.getStatus() == ITestResult.SUCCESS){
			test.pass(MarkupHelper.createLabel("Test case "+iTestResult.getName() + ": PASSED", ExtentColor.GREEN));
		}else if (iTestResult.getStatus() == ITestResult.FAILURE){
			test.fail(MarkupHelper.createLabel("Test case "+iTestResult.getName() + ": FAILED", ExtentColor.RED));
			test.fail(iTestResult.getThrowable());
		}
		
	}
	
	
		
}
