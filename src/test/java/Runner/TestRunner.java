package Runner;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import Reporting.ExtentManager;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "Features",
        glue = {"stepDefinitions", "Reporting"}
)

public class TestRunner {

    @BeforeClass
    public static void beforeClass() {

    }

    @AfterClass
    public static void afterClass() {
        ExtentManager.getExtentReports().flush();
        System.out.println("Test Execution Completed!");
    }


}
