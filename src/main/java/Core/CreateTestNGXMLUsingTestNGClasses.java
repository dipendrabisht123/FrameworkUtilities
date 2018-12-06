package Core;

import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import TestClasses.LogInToGoogle;

public class CreateTestNGXMLUsingTestNGClasses {

	public static void main(String[] args) {
		XmlSuite xmlSuite = new XmlSuite();
		xmlSuite.setName("Suite");
		xmlSuite.setVerbose(10);
		xmlSuite.addListener("Listners.MySuitListener");
		xmlSuite.setFileName("myXML");
		
		
		XmlTest xmlTest = new XmlTest(xmlSuite);
		xmlTest.setName("Test-1");
		xmlTest.setPreserveOrder(true);
		//xmlTest.setParameters(params);
		
		
		XmlClass LogInToGoogle = new XmlClass(LogInToGoogle.class);
		
		List<XmlClass> clsList = new ArrayList<XmlClass>();
		clsList.add(LogInToGoogle);
		
		xmlTest.setXmlClasses(clsList);
		
		
		List<XmlSuite> suiteList = new ArrayList<XmlSuite>();
		suiteList.add(xmlSuite);
		
		TestNG testNG = new TestNG();
		testNG.setXmlSuites(suiteList);
		
		testNG.run();

	}

}
