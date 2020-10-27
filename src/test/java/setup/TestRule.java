package setup;

import utils.Log4jUtils;
import utils.ReportUtils;
import utils.SeleniumUtils;
import com.aventstack.extentreports.Status;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class TestRule {
  protected static WebDriver driver;

  @Before
  public void beforeCenario(Scenario s) {

    ReportUtils.createReport(s);
    Log4jUtils.createLog(s);

    ChromeOptions options = new ChromeOptions();
    String headless = SeleniumUtils.loadFromPropertiesFile("dell.properties",
        "CHROME_HEADLESS");

    String projectPath = System.getProperty("user.dir");
    String os =  System.getProperty("os.name");

      if (os.toUpperCase().contains("WINDOWS")) {
        System.setProperty("webdriver.chrome.driver", projectPath + "/drivers/chromedriver.exe");
        System.setProperty("os.download.path", System.getProperty("user.home").replaceAll("\\\\", "/") + "/Downloads/");
      } else {
        System.setProperty("webdriver.chrome.driver", projectPath + "/drivers/chromedriver");
        System.setProperty("os.download.path", System.getProperty("user.home") + "/Downloads/");
      }

      if (headless.equals("TRUE")) options.addArguments("headless");

      options.addArguments("--no-sandbox");
      options.addArguments("--disable-dev-shm-usage");
      options.addArguments("window-size=1920x1080");
      options.addArguments("--incognito");

      driver = new ChromeDriver(options);

    driver.manage().deleteAllCookies();
    driver.manage().window().maximize();

    String scenarioName = s.getName();
    ReportUtils.reportMessage(Status.INFO, "Running test scenario : " + scenarioName);
    Log4jUtils.logMessage("INFO", "Running test scenario : " + scenarioName);
  }

  public static WebDriver getDriver() {
    return driver;
  }

  @After
  public void afterCenario(Scenario scenario) {
    ReportUtils.reportMessage(Status.INFO, "Closing chromeDriver",
        SeleniumUtils.getScreenshotReport());
    Log4jUtils.logMessage("INFO", "Closing chromeDriver");
    driver.quit();
    ReportUtils.refreshReport(scenario);
  }


}
