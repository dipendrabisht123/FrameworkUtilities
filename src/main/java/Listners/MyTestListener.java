package Listners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class MyTestListener implements ITestListener{

	@Override
	public void onStart(ITestContext context) {
		System.out.println("In ITestListener -> onStart.... "+context.getCurrentXmlTest().getName()+" started");
		
	}
	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("In ITestListener -> onTestStart....Test "+ result.getName() +" started.");
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("In ITestListener -> onTestSuccess.... "+result.getName()+" result : "+result.getStatus());
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println("In ITestListener -> onTestFailure.... "+result.getTestContext().getName()+" result : "+result.getStatus());
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("In ITestListener -> onTestSkipped.... "+result.getTestName()+" result : "+result.getStatus() );
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println("onTestFailedButWithinSuccessPercentage INVOKED");
		
	}

	

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

}
