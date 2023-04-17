package QKART_TESTNG;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListenerClass extends QKART_Tests implements ITestListener{

    public void onTestStart(ITestResult result){
        takeScreenshot(driver,"onTestStart",result.getName());
    }

    public void onTestSuccess(ITestResult result){
        takeScreenshot(driver, "onTestSuccess", result.getName());
    }
    public void onTestFailure(ITestResult result){
        takeScreenshot(driver, "onTestFailure", result.getName());
    }
}