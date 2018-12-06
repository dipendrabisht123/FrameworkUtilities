package Listners;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.IExecutionListener;

public class MyExecutionListener implements IExecutionListener{

	private long startTime;
	private long endTime;
	SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
	
	public void onExecutionStart() {
		startTime=System.currentTimeMillis();
		    
		Date resultdate = new Date(startTime);
		
		System.out.println("Execution started at: "+(sdf.format(resultdate)));
		
	}

	public void onExecutionFinish() {
		endTime=System.currentTimeMillis();
		Date date = new Date(endTime);
		System.out.println("Execution started at: "+(sdf.format(date)) + "| total time taken: "+(endTime-startTime));
		
	}

}
