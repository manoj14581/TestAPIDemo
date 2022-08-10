package Reporting;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import cucumber.api.Result;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.runtime.ScenarioImpl;
import org.apache.commons.lang.reflect.FieldUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

public class Report {
    public static Logger logger = Logger.getLogger(Report.class);
    public static ThreadLocal<Scenario> CURRENT_SCENARIO = new ThreadLocal<>();
    public static ThreadLocal<String> stepMessage = new ThreadLocal<>();
    public static ThreadLocal<Integer> currentStepIndex = new ThreadLocal<>();
    public static ThreadLocal<String> currentStep = new ThreadLocal<>();
    public static StringBuilder messageBuilder = new StringBuilder();

    public static void createStep(String... data) {
        logger.info(ExtentTestManager.featureFileName.get()+" => " + data[0]);
        beforeStep(data);
    }

    public static void recursiveDelete(File file) {
        if (!file.exists())
            return;
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                recursiveDelete(f);
            }
        }
        file.delete();
    }

    static {
        try {
            File extentReports = new File("./extent-reports");
            if(extentReports.exists())
                recursiveDelete(extentReports);
            String logPropertyFileName = "src/test/resources/config/log4j.properties";
            PropertyConfigurator.configure(logPropertyFileName);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @Before
    public void beforeScenario(Scenario scenario) throws Exception {
        String featureFileName = scenario.getUri().substring(scenario.getUri().lastIndexOf("/")+1);
        ExtentTestManager.setFeatureFileName(featureFileName);
        logger.info(featureFileName+" => " + scenario.getName());
        messageBuilder.setLength(0);

        logger.info("----------------------- TEST STARTED -----------------------");
        currentStepIndex.set(0);
        CURRENT_SCENARIO.set(scenario);
        ExtentTest test = ExtentTestManager.startTest(scenario.getName(), scenario.getName());
        ExtentTestManager.assignCategory(test);
    }

    public static void beforeStep(String... stepName) {
        int index = currentStepIndex.get();
        if(index>0) {
            afterStep();
        }
        currentStep.set(stepName[0]);
        currentStepIndex.set(index+1);
    }

    public static void afterStep() {
        String step = currentStep.get();
        if(CURRENT_SCENARIO.get().isFailed())
            ExtentTestManager.getTest().log(Status.FAIL,step);
        else
            ExtentTestManager.getTest().log(Status.PASS,step);
        currentStep.set(null);
        if(messageBuilder.length()>0)
            messageBuilder.append("\n");
        messageBuilder.append(stepMessage.get());
    }

    @After
    public void afterScenario(Scenario scenario) {
        if(!scenario.isFailed())
            afterStep();
        else
            logErrorInReport(scenario, messageBuilder.toString());
        currentStepIndex.set(0);
        logger.info(ExtentTestManager.featureFileName.get()+" => " + scenario.getName());
        logger.info("---------------------- TEST COMPLETED ----------------------");
    }

    private static void logErrorInReport(Scenario scenario, String errorMessage) {
        Field field = FieldUtils.getField(((ScenarioImpl) scenario).getClass(), "stepResults", true);
        field.setAccessible(true);
        try {
            ArrayList<Result> results = (ArrayList<Result>) field.get(scenario);
            for (Result result : results) {
                if (result.getError() != null)
                    ReportUtil.writeReportLog(false,currentStep.get(),result.getError() + "\n" + errorMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ReportUtil.writeReportLog(false,currentStep.get(),e.getMessage()+", Error while logging error: "+ Arrays.toString(e.getStackTrace()));
        }
    }
}
