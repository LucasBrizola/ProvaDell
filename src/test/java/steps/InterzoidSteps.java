package steps;

import actions.InterzoidAction;
import com.aventstack.extentreports.Status;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import utils.Log4jUtils;
import utils.ReportUtils;

import static org.junit.Assert.assertTrue;

public class InterzoidSteps {
    private final InterzoidAction interzoidAction = new InterzoidAction();

    @When("^i make a GET requisition to the API's endpoint for the city \"([^\"]*)\" and state \"([^\"]*)\"$")
    public void requisitionToWeatherApi(String city, String state) {
        ReportUtils.reportMessage(Status.INFO, "Realizing request to Interzoid weather api.");
        interzoidAction.generateRequistion(city, state);
    }

    @Then("^i will verify it's Status as \"([^\"]*)\"$")
    public void verifyResponseFromWeatherApi(String expectedOutcome) {
        ReportUtils.reportMessage(Status.INFO, "Validating API Status.");
        Log4jUtils.logMessage("INFO", "Validating API Status.");
        assertTrue(interzoidAction.ValidateApiStatus(expectedOutcome));
        ReportUtils.reportMessage(Status.INFO, "Validating API Description.");
        Log4jUtils.logMessage("INFO", "Validating API Description.");
        assertTrue(interzoidAction.ValidateApiDescription(expectedOutcome));
    }
}
