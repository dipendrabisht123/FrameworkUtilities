package Core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.TestNG;
import org.testng.reporters.jq.TestNgXmlPanel;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.codoid.products.exception.FilloException;

import PageClasses.Utilities.CreateTestNGXML;
import PageClasses.Utilities.ExcelUtilities.ReadExcel;
import TestClasses.LogInToGoogle;

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
