package Listners;

import org.testng.ISuite;
import org.testng.ISuiteListener;

public class MySuitListener implements ISuiteListener{

	public void onFinish(ISuite arg0) {
		System.out.println("Suit execution finished...");
		
	}

	public void onStart(ISuite arg0) {
		System.out.println("Suit execution started...");
		
	}
	

}
