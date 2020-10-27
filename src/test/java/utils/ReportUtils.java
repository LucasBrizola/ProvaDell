package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import io.cucumber.java.Scenario;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportUtils extends SeleniumUtils {

  public static ExtentHtmlReporter htmlReporter;
  public static ExtentReports extentReports;
  public static ExtentTest child;
  public static String directoryReport;

  public static void createReport(Scenario scenario) {
    if (extentReports == null) {
      extentReports = new ExtentReports();
      String dir = System.getProperty("user.dir");
      String filename = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
      filename = filename.replace(".", "_");
      SeleniumUtils.createDirectory(dir + "\\report");
      setDirectoryReport("./report/" + filename);
      SeleniumUtils.createDirectory(directoryReport);

      htmlReporter = new ExtentHtmlReporter(directoryReport + "\\report.html");
      extentReports.attachReporter(htmlReporter);
    }
    child = extentReports.createTest(scenario.getName(),
        "" );
  }

  public static void reportMessage(Status status, String message) {
    System.out.println(message);
    child.log(status, message);
    extentReports.flush();
  }

  public static void reportMessage(Status status, String message, String image) {
    try {
      System.out.println(message);
      child.log(status, message, MediaEntityBuilder.createScreenCaptureFromPath(image).build());
      extentReports.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void setDirectoryReport(String diretorio) {
    directoryReport = diretorio;
  }

  public static void refreshReport(Scenario scenario) {
    if (scenario.isFailed()) {
      child.log(Status.ERROR, "Error found during execution.");
    } else {
      child.log(Status.PASS, "Scenario ran with success.");
    }
    extentReports.flush();
  }

  public static String getDirectoryReport() {
    return directoryReport;
  }
}
