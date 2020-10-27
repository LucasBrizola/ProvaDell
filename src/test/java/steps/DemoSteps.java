package steps;

import actions.DemostoreAction;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.DemostorePage;

import static org.junit.Assert.assertTrue;

public class DemoSteps {

    private final DemostorePage demostorePage = new DemostorePage();
    private final DemostoreAction demostoreAction = new DemostoreAction();

    @Given("i access cs-cart website")
    public void accessDemostore() {
        demostorePage.accessDemostore();
    }

    @When("^i search for an \"([^\"]*)\"$")
    public void informaDadosLogin(String item) {
        demostoreAction.searchItem(item);
    }

    @And("find this product on the grid")
    public void findItemOnGrid(){
        demostoreAction.findItemOnGrid();
    }

    @And("i add it to the cart")
    public void addItemToCart(){
        demostoreAction.addItemToCart();
    }

    @Then("i check the item on the chart and the total price")
    public void checkChart(){
        demostoreAction.accessChart();
        assertTrue(demostoreAction.checkChart());
        assertTrue(demostoreAction.checkPrice());
    }

}
