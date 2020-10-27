package pages;

import utils.Log4jUtils;
import utils.ReportUtils;
import com.aventstack.extentreports.Status;
import utils.SeleniumUtils;

import java.util.Objects;

public class InterzoidPage extends SeleniumUtils {

    public boolean validateResponseStatus(int expectedOutcome, int statusCode) {
        ReportUtils.reportMessage(Status.INFO, "status code expected from api: " + expectedOutcome);
        ReportUtils.reportMessage(Status.INFO, "status code received from api: " + statusCode);
        Log4jUtils.logMessage("INFO", "status code expected from api: " + expectedOutcome);
        Log4jUtils.logMessage("INFO", "status code received from api: " + statusCode);
        return Objects.equals(expectedOutcome, statusCode);
    }
    public boolean validateApiResponse(String expectedOutcome, String description) {
        String expectedDescription = loadFromPropertiesFile("dell.properties",
                expectedOutcome);
        ReportUtils.reportMessage(Status.INFO, "description expected from api: " + expectedDescription);
        ReportUtils.reportMessage(Status.INFO, "description received from api: " + description);
        Log4jUtils.logMessage("INFO", "description expected from api: " + expectedDescription);
        Log4jUtils.logMessage("INFO", "description received from api: " + description);
        return description.contains(expectedDescription);
    }
}
