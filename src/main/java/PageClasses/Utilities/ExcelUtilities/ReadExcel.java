package PageClasses.Utilities.ExcelUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;



public class ReadExcel {
	
	public static Map<String, String> mapConfigData = new HashMap<String, String>();
	public static List <HashMap<String, String>> runnerDetails = new ArrayList<HashMap<String,String>>();
	
	
	 
	public static HashMap<String, String> fetchComponentDetails(String fileName, String componentName) throws FilloException{
		
		HashMap<String, String> componentDetail = new HashMap<String, String>();
		
		Fillo fillo = new Fillo();
		Connection conn = fillo.getConnection(fileName);
		
		String query = "Select * from Components where Component = '"+componentName+"'";
		System.out.println("Fetch Component query: " + query);
		Recordset recordset = conn.executeQuery(query);
				
		List<String> fieldNames = recordset.getFieldNames();
		recordset.next();
		
		for (String field : fieldNames){
			componentDetail.put(field, recordset.getField(field));
		}
		
		return componentDetail;
		
	}
	
	/**
	 * Description: Bases upon the provided runnerDetail, it fetches all the steps of the test case 
	 * @param fileName
	 * @param runnerDetail
	 * @return
	 * @throws Exception
	 */
	public static SortedMap<Integer, HashMap<String, String>> fetchTestCaseSteps (String fileName, HashMap<String, String> runnerDetail) throws Exception{
		SortedMap<Integer, HashMap<String, String>> testCaseSteps = new TreeMap<Integer, HashMap<String,String>>();
		HashMap<String, String> componentDetails;
		
		Fillo fillo = new Fillo();
		Connection conn = fillo.getConnection(fileName);
		
		//String query = "Select InternalStepID, Component, DataSheets, DataSheetId from Tests where InternalStepID = '"+runnerDetail.get("TESTCASEID")+"'";
		String query = "Select StepId, InternalStepID, Component, DataSheets, DataSheetId from Tests where TestCaseID = '"+runnerDetail.get("TESTCASEID")+"'";
		System.out.println("query: "+query);
		
		Recordset recordset = conn.executeQuery(query);
	
		List<String> fieldNames = new ArrayList<String>();
		fieldNames = recordset.getFieldNames();
		
		while (recordset.next()){
			if (Integer.parseInt(recordset.getField("StepId")) > 0){
				componentDetails = new HashMap<String, String>();
				
				for (String field : fieldNames){
					componentDetails.put(field, recordset.getField(field));
				}
				testCaseSteps.put(Integer.valueOf(recordset.getField("StepId")), componentDetails);
			}
			
			
			
		}
		
		return testCaseSteps;
		
	}
		
	/**
	 * Fetches details of test cases with RunFlag = Yes
	 * @param fileName
	 * @throws FilloException
	 */
	public static void  fetchRunnerDetails(String fileName) throws FilloException{
		
		HashMap<String, String> testCase;
		
		Fillo fillo = new Fillo();
		Connection connection = fillo.getConnection(fileName);// "./TestData.xlsx"

		String strQuery = "Select TestCaseID, TestCaseName from Runner where RunFlag= 'Yes'";
		Recordset recordset = connection.executeQuery(strQuery);

		List<String> fieldNames = new ArrayList<String>();
		fieldNames = recordset.getFieldNames();
		
		while (recordset.next()) {
			testCase = new HashMap<String, String>();
			for (String field : fieldNames){
				testCase.put(field, recordset.getField(field));
			}
			
			runnerDetails.add(testCase);
		}

		recordset.close();
		connection.close();
	}

	
	/**
	 * Description: Fetches an excel details (data) and returns it in form of Map 
	 * @param fileName
	 * @param sheet
	 * @param key
	 * @return
	 * @throws FilloException
	 */
	public static Map<String, String> fetchExcel(String fileName, String sheet, String key) throws FilloException{
		Fillo fillo=new Fillo();
		Connection connection=fillo.getConnection(fileName);// "./TestData.xlsx"
		String strQuery="Select * from "+sheet+" where ID="+key;
		Recordset recordset=connection.executeQuery(strQuery);
		
		List<String> fieldNames = new ArrayList<String>();
		
		fieldNames = recordset.getFieldNames();
		
		Map<String, String> mapTestData = new HashMap<String, String>();
		
		while(recordset.next()){
			for (String field: fieldNames){
				mapTestData.put(field, recordset.getField(field));
			}
		
		}

		recordset.close();
		connection.close();
		
		return mapTestData;

	}

	/**
	 * Description: Fetches config details from Config sheet
	 * @param fileName
	 * @throws FilloException
	 */
	public static void fetchConfigDetails(String fileName) throws FilloException{
		Fillo fillo = new Fillo();
		Connection connection = fillo.getConnection(fileName);// "./TestData.xlsx"

		String strQuery = "Select * from Config";
		Recordset recordset = connection.executeQuery(strQuery);

		List<String> fieldNames = new ArrayList<String>();
		fieldNames = recordset.getFieldNames();

		

		while (recordset.next()) {
			mapConfigData.put(recordset.getField(fieldNames.get(0)), recordset.getField(fieldNames.get(1)));
		}

		recordset.close();
		connection.close();

	}


	

}
