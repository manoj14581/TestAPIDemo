package Reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
    private static ExtentReports extentReports;

    public synchronized static ExtentReports getExtentReports() {
        if(extentReports==null) {
            extentReports = new ExtentReports();
            ExtentSparkReporter reporter = new ExtentSparkReporter("./extent-reports/extent-report.html");
            reporter.config().setReportName("Test Automation Report");
            reporter.config().setTheme(Theme.STANDARD);
            reporter.config().setDocumentTitle("Test Automation Report");
            reporter.config().setEncoding("utf-8");
            extentReports.attachReporter(reporter);
            extentReports.setSystemInfo("Author", "Test Automation");
        }
        return extentReports;
    }
}
