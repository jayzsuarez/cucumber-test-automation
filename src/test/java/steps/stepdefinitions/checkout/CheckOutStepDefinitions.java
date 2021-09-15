package steps.stepdefinitions.checkout;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import net.thucydides.core.annotations.Steps;
import steps.checkout.CheckOuSteps;

public class CheckOutStepDefinitions {

    @Steps
    CheckOuSteps checkOuSteps;


    @Given("User proceed to check-out")
    public void userProceedToCheckOut() {
        checkOuSteps.proceedToCheckOut();
    }

    @Then("User able to complete place an order")
    public void completePlacingAnOrder() {
        checkOuSteps.completeOrder();
    }

}
