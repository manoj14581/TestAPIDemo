package Reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import java.util.HashMap;
import java.util.Map;

public class ExtentTestManager {
    public static Map<Integer, ExtentTest> extentTestMap = new HashMap<>();
    public static ExtentReports extent = ExtentManager.getExtentReports();
    public static ThreadLocal<String> featureFileName = new ThreadLocal<>();
    public static synchronized ExtentTest getTest() {
        return extentTestMap.get((int) Thread.currentThread().getId());
    }

    public static synchronized ExtentTest startTest(String testName, String desc) {
        ExtentTest test = extent.createTest(testName, desc);
        extentTestMap.put((int) Thread.currentThread().getId(), test);
        return test;
    }

    public static synchronized void assignCategory(ExtentTest test) {
        if(featureFileName!=null && featureFileName.get()!=null)
            test.assignCategory(featureFileName.get());
    }

    public static synchronized void setFeatureFileName(String name) {
        featureFileName.set(name);
    }


}
