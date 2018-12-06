package Core;

import java.util.ArrayList;
import org.testng.TestNG;
import PageClasses.Utilities.CreateTestNGXML;
import PageClasses.Utilities.ExcelUtilities.ReadExcel;

public class Main {
	
	public static String testDataFileName = "./TestData.xlsx";
	public static void main(String[] args) throws Exception {
		
		ReadExcel.fetchConfigDetails(testDataFileName);
		
		ReadExcel.fetchRunnerDetails(testDataFileName);
		
		CreateTestNGXML.generateTestNGXML_UsingExcelData_V2();
		
		ArrayList<String> testNGXML = new ArrayList<String>();
		testNGXML.add("A:\\STUDY\\Codes\\Workspace_Neon\\FrameworkUtilities\\myTestng.xml");
		
		TestNG testNG = new TestNG();
		testNG.setTestSuites(testNGXML);
		
		testNG.run();
		
		
		
		
	}
}
