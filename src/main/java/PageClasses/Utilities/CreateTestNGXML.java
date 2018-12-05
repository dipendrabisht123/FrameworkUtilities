package PageClasses.Utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import PageClasses.Utilities.ExcelUtilities.ReadExcel;

public class CreateTestNGXML {

	
	
	/**
	 * Creates testNG XML with excel values but with new Runner sheet (assuming each @Test method (called in Tests sheet under the test case) as one testNG test ) 
	 * @throws Exception
	 */
	public static void generateTestNGXML_UsingExcelData_V3() throws Exception{
		
		int priority = 0;
		String sPriority = null;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		
		Document doc = dBuilder.newDocument();
		
		// Creating root element of .xml
		Element rootElement = doc.createElement("suite");
		rootElement.setAttribute("name", "Regression");
		//rootElement.setAttribute("parallel", "false");
		
		// appending root element to doc
		doc.appendChild(rootElement);
		
		// Create listeners node
		Element listeners = doc.createElement("listeners");
		
		rootElement.appendChild(listeners);
		
		Element listener = doc.createElement("listener");
		listener.setAttribute("class-name", "Listners.MyExecutionListener");
		
		listeners.appendChild(listener);
					
		int noOfTestCases = ReadExcel.runnerDetails.size();
		
		for (int i=0; i<noOfTestCases; i++){
			
			String testCaseName = ReadExcel.runnerDetails.get(i).get("TESTCASENAME");
			
			
			// fetching steps of test case from Test Sheet
			SortedMap<Integer, HashMap<String, String>> testCaseSteps = new TreeMap<Integer, HashMap<String,String>>();
			testCaseSteps = ReadExcel.fetchTestCaseSteps("./TestData.xlsx", ReadExcel.runnerDetails.get(i));
			
			// Iterate each test step to get the component details and attached test data
			Set<Map.Entry<Integer, HashMap<String,String>>> entrySet = testCaseSteps.entrySet();
			Iterator<Map.Entry<Integer, HashMap<String,String>>> it = entrySet.iterator();
			
			HashMap<String, String> componentDetail;
			Element classElement;
			while (it.hasNext()){
				Map.Entry<Integer, HashMap<String,String>> entry = it.next();
				componentDetail = ReadExcel.fetchComponentDetails("./TestData.xlsx", entry.getValue().get("COMPONENT"));
				
				// Creating test node of suite element
				Element testElement = doc.createElement("test"); 
				testElement.setAttribute("name", componentDetail.get("Class")+String.valueOf(entry.getKey()));
				testElement.setAttribute("preserve-order", "true");
				//testElement.setAttribute("group-by-instances", "true");
				
				
				// appendng child to root
				rootElement.appendChild(testElement);
				
				// Create classes node
				Element classes = doc.createElement("classes");
				testElement.appendChild(classes);
				
				
				
				classElement = doc.createElement("class");
				classElement.setAttribute("name", componentDetail.get("Package")+"."+componentDetail.get("Class"));
				//classElement.setAttribute("priority", String.valueOf(entry.getKey()));
				
				classes.appendChild(classElement);
				
				// Class level parameters
				Element clsParameter_StepID = doc.createElement("parameter");
				clsParameter_StepID.setAttribute("name", "InternalStepID");
				clsParameter_StepID.setAttribute("value", String.valueOf(entry.getKey()));
				classElement.appendChild(clsParameter_StepID);
				
				// Methods node
				Element methods = doc.createElement("methods");
				classElement.appendChild(methods);
				
				// Method
				Element include = doc.createElement("include");
				include.setAttribute("name", componentDetail.get("Method"));
				
				methods.appendChild(include);
				
				String[] dataSheets = entry.getValue().get("DATASHEETS").split(";");
				String[] dataSheetIDs = entry.getValue().get("DATASHEETID").split(";");
				
				// Fetch data passed to component, from data sheets
				Map<String, String> methodLevelParameters = null;
				
				for (int iSheet=0; iSheet < dataSheets.length; iSheet++){
					methodLevelParameters = ReadExcel.fetchExcel("./TestData.xlsx", "Login", dataSheetIDs[iSheet]);
				}
				
				Set<String> keySet = methodLevelParameters.keySet();
				
				Iterator<String> parameterIterator = keySet.iterator();
				
				// Method level parametrs
				Element componentParameter;
				while (parameterIterator.hasNext()){
					
					String parameterName = parameterIterator.next();
					componentParameter = doc.createElement("parameter");
					componentParameter.setAttribute("name", parameterName);
					componentParameter.setAttribute("value", methodLevelParameters.get(parameterName));
					
					include.appendChild(componentParameter);
				}
				
				
				
			}
			
		}
					
		// **************************************************************************************************
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "http://testng.org/testng-1.0.dtd");
		
		DOMSource source = new DOMSource(doc);
		
		StreamResult file = new StreamResult(new File("A:\\STUDY\\Codes\\Workspace_Neon\\FrameworkUtilities\\myTestng.xml")); 
		
		transformer.transform(source, file);
	}
	
	
	/**
	 * Creates testNG XML with excel values but with new Runner sheet (assuming @Test method as one component of the test case) 
	 * @throws Exception
	 */
	public static void generateTestNGXML_UsingExcelData_V2() throws Exception{
		
		int priority = 0;
		String sPriority = null;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		
		Document doc = dBuilder.newDocument();
		
		// Creating root element of .xml
		Element rootElement = doc.createElement("suite");
		rootElement.setAttribute("name", ReadExcel.mapConfigData.get("projectName"));
		//rootElement.setAttribute("parallel", "false");
		
		// appending root element to doc
		doc.appendChild(rootElement);
		
		// Create listeners node
		Element listeners = doc.createElement("listeners");
		
		rootElement.appendChild(listeners);
		
		Element listener = doc.createElement("listener");
		listener.setAttribute("class-name", "Listners.MyExecutionListener");
		
		listeners.appendChild(listener);
		
		int noOfTestCases = ReadExcel.runnerDetails.size();
		
		for (int i=0; i<noOfTestCases; i++){
			
			String testCaseName = ReadExcel.runnerDetails.get(i).get("TESTCASENAME");
			
			// Creating test node of suite element
			Element testElement = doc.createElement("test"); 
			testElement.setAttribute("name", testCaseName);
			testElement.setAttribute("preserve-order", "true");
			//testElement.setAttribute("group-by-instances", "true");
			
			
			
			// appendng child to root
			rootElement.appendChild(testElement);
			
			// Create classes node
			Element classes = doc.createElement("classes");
			testElement.appendChild(classes);
			
			// fetching steps of test case from Test Sheet
			SortedMap<Integer, HashMap<String, String>> testCaseSteps = new TreeMap<Integer, HashMap<String,String>>();
			testCaseSteps = ReadExcel.fetchTestCaseSteps("./TestData.xlsx", ReadExcel.runnerDetails.get(i));
			
			// Iterate each test step to get the component details and attached test data
			Set<Map.Entry<Integer, HashMap<String,String>>> entrySet = testCaseSteps.entrySet();
			Iterator<Map.Entry<Integer, HashMap<String,String>>> it = entrySet.iterator();
			
			HashMap<String, String> componentDetail;
			Element classElement;
			while (it.hasNext()){
				Map.Entry<Integer, HashMap<String,String>> entry = it.next(); 
				
				componentDetail = ReadExcel.fetchComponentDetails("./TestData.xlsx", entry.getValue().get("COMPONENT"));
				
				classElement = doc.createElement("class");
				classElement.setAttribute("name", componentDetail.get("Package")+"."+componentDetail.get("Class"));
				//classElement.setAttribute("priority", String.valueOf(entry.getKey()));
				
				/*sPriority = String.valueOf(++priority);
				System.out.println("sPriority: "+ sPriority);
				classElement.setAttribute("priority", sPriority);*/
				classes.appendChild(classElement);
				
				// Class level parameters
				Element clsParameter_StepID = doc.createElement("parameter");
				clsParameter_StepID.setAttribute("name", "InternalStepID");
				clsParameter_StepID.setAttribute("value", String.valueOf(entry.getKey()));
				classElement.appendChild(clsParameter_StepID);
				
				
				String[] dataSheets = entry.getValue().get("DATASHEETS").split(";");
				String[] dataSheetIDs = entry.getValue().get("DATASHEETID").split(";");
				
				// Fetch data passed to component, from data sheets
				Map<String, String> methodLevelParameters = null;
				
				for (int iSheet=0; iSheet < dataSheets.length; iSheet++){
					methodLevelParameters = ReadExcel.fetchExcel("./TestData.xlsx", "Login", dataSheetIDs[iSheet]);
				}
				
				Set<String> keySet = methodLevelParameters.keySet();
				
				Iterator<String> parameterIterator = keySet.iterator();
				
				// Method level parametrs
				Element componentParameter;
				while (parameterIterator.hasNext()){
					
					String parameterName = parameterIterator.next();
					componentParameter = doc.createElement("parameter");
					componentParameter.setAttribute("name", parameterName);
					componentParameter.setAttribute("value", methodLevelParameters.get(parameterName));
					
					classElement.appendChild(componentParameter);
				}
				
				/*// Methods node
				Element methods = doc.createElement("methods");
				classElement.appendChild(methods);
				
				Element include = doc.createElement("include");
				include.setAttribute("name", componentDetail.get("Method"));
				
				methods.appendChild(include);*/
				
			}
			
		}
					
		// **************************************************************************************************
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "http://testng.org/testng-1.0.dtd");
		
		DOMSource source = new DOMSource(doc);
		
		StreamResult file = new StreamResult(new File("A:\\STUDY\\Codes\\Workspace_Neon\\FrameworkUtilities\\myTestng.xml")); 
		
		transformer.transform(source, file);
	}
	
	/**
	 * Creates testNG xml with excel values but with old Runner sheet (assuming @Test method as one test case) 
	 * @throws Exception
	 */
	/*public static void generateTestNGXML_UsingExcelData_V1() throws Exception{
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		
		Document doc = dBuilder.newDocument();
		
		// Creating root element of .xml
		Element rootElement = doc.createElement("suite");
		rootElement.setAttribute("name", ReadExcel.mapConfigData.get("projectName"));
		// appending root element to doc
		doc.appendChild(rootElement);
		
		// Create listeners node
		Element listeners = doc.createElement("listeners");
		
		rootElement.appendChild(listeners);
		
		Element listener = doc.createElement("listener");
		listener.setAttribute("class-name", "Listners.MyExecutionListener");
		
		listeners.appendChild(listener);
		
		Set<String> setTestCases = ReadExcel.testCaseDetails.keySet();
		
		
		
		Iterator it = setTestCases.iterator();
		
		while (it.hasNext()){
			String testCaseName = (String) it.next();
			// Creating test node of suite element
			Element testElement = doc.createElement("test"); 
			testElement.setAttribute("name", testCaseName);
			
			// appendng child to root
			rootElement.appendChild(testElement);
			
			// Create classes node
			Element classes = doc.createElement("classes");
			testElement.appendChild(classes);
			
			Element classElement = doc.createElement("class");
			classElement.setAttribute("name", ReadExcel.testCaseDetails.get(testCaseName).get("Package")+"."+ReadExcel.testCaseDetails.get(testCaseName).get("Class"));
			
			classes.appendChild(classElement);
			
			// Class level parameters
			Element clsParameter1 = doc.createElement("parameter");
			clsParameter1.setAttribute("name", "userName");
			clsParameter1.setAttribute("value", "Deep");
			
			classElement.appendChild(clsParameter1);
			
			// Methods node
			Element methods = doc.createElement("methods");
			classElement.appendChild(methods);
			
			Element include = doc.createElement("include");
			include.setAttribute("name", ReadExcel.testCaseDetails.get(testCaseName).get("TestCaseName"));
			
			methods.appendChild(include);
			
			// Method level parametrs
			Element methodParameter1 = doc.createElement("parameter");
			methodParameter1.setAttribute("name", "data-id");
			methodParameter1.setAttribute("value", "Poonam");
			
			include.appendChild(methodParameter1);
		}
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "http://testng.org/testng-1.0.dtd");
		
		DOMSource source = new DOMSource(doc);
		
		StreamResult file = new StreamResult(new File("A:\\STUDY\\Codes\\Workspace_Neon\\FrameworkUtilities\\myTestng.xml")); 
		
		transformer.transform(source, file);
	}*/
	
	
	/**
	 * Description: Creates testNG xml with hardcoaded values
	 * @throws Exception
	 */
	public static void generateTestNGXML() throws Exception{
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		
		Document doc = dBuilder.newDocument();
		
		// Creating root element of .xml
		Element rootElement = doc.createElement("suite");
		rootElement.setAttribute("name", "Regression");
		// appending root element to doc
		doc.appendChild(rootElement);
		
		// Create listeners node
		Element listeners = doc.createElement("listeners");
		
		rootElement.appendChild(listeners);
		
		Element listener = doc.createElement("listener");
		listener.setAttribute("class-name", "Listners.MyExecutionListener");
		
		listeners.appendChild(listener);
		
		// Creating test node of suite element
		Element testElement = doc.createElement("test"); 
		testElement.setAttribute("name", "Test-1");
		// appendng child to root
		rootElement.appendChild(testElement);
		
		// Create listeners node
				Element classes = doc.createElement("classes");
				
				testElement.appendChild(classes);
				
				Element classElement = doc.createElement("class");
				classElement.setAttribute("name", "TestClasses.LogInToGoogle");
				
				classes.appendChild(classElement);
				
				Element clsParameter1 = doc.createElement("parameter");
				clsParameter1.setAttribute("name", "userName");
				clsParameter1.setAttribute("value", "Deep");
				
				classElement.appendChild(clsParameter1);
				
				
				Element methods = doc.createElement("methods");
				classElement.appendChild(methods);
				
				Element include = doc.createElement("include");
				include.setAttribute("name", "loginGoogle");
				methods.appendChild(include);
				
				
				Element methodParameter1 = doc.createElement("parameter");
				methodParameter1.setAttribute("name", "data-id");
				methodParameter1.setAttribute("value", "Poonam");
				
				include.appendChild(methodParameter1);
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "http://testng.org/testng-1.0.dtd");
		
		DOMSource source = new DOMSource(doc);
		
		StreamResult file = new StreamResult(new File("A:\\STUDY\\Codes\\Workspace_Neon\\FrameworkUtilities\\myTestng.xml")); 
		
		transformer.transform(source, file);
	}

}
