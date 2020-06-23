package Core;

import java.util.ArrayList;
import org.testng.TestNG;
import PageClasses.Utilities.CreateTestNGXML;
import PageClasses.Utilities.ExcelUtilities.ReadExcel;

public class Main {
	
	public static String testDataFileName = System.getProperty("user.dir")+"\\TestData.xlsx";
	public static void main(String[] args) throws Exception {
		
		final String ESCAPE_PROPERTY = "org.uncommons.reportng.escape-output";
        System.setProperty(ESCAPE_PROPERTY, "false");
		
		ReadExcel.fetchConfigDetails(testDataFileName);
		
		ReadExcel.fetchRunnerDetails(testDataFileName);
		
		CreateTestNGXML.generateTestNGXML_UsingExcelData_V2();
		
		ArrayList<String> testNGXML = new ArrayList<String>();
		
		System.out.println("testNG XML file path: "+System.getProperty("user.dir")+"\\myTestng.xml");
		
		// All 3 ways of getting myTestng.xml are valid
		testNGXML.add(System.getProperty("user.dir")+"\\myTestng.xml");
//		testNGXML.add("./myTestng.xml");
//		testNGXML.add("A:\\STUDY\\Codes\\Workspace_Neon\\FrameworkUtilities\\myTestng.xml");
		
		
		TestNG testNG = new TestNG();
		testNG.setTestSuites(testNGXML);
		
		testNG.run();
		
	}
}
