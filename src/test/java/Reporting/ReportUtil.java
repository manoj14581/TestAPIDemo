package Reporting;


public class ReportUtil {

    public static void writeReportLog(boolean success, String step, String log) {
        if(!success) {
            StringBuilder markup = new StringBuilder("<details><summary><b><font color=red>"
                    +step+"</font></b></summary>"+log.replaceAll("\n","<br>")+"</details>");
            ExtentTestManager.getTest().fail(markup.toString());
        } else {
            StringBuilder markup = new StringBuilder("<details><summary><b><font color=black>"
                    +step+"</font></b></summary>"+log.replaceAll("\n","<br>")+"</details>");
            ExtentTestManager.getTest().pass(markup.toString());

        }
    }

    public static void writeReportSkipLog(String step, String log) {
        StringBuilder markup = new StringBuilder("<details><summary><b><font color=gray>"
                +step+"</font></b></summary>"+log.replaceAll("\n","<br>")+"</details>");
        ExtentTestManager.getTest().skip(markup.toString());
    }

}
