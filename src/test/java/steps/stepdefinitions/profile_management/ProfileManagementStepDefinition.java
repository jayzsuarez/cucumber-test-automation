package steps.stepdefinitions.profile_management;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;
import steps.profile_management.ProfileManagementSteps;

public class ProfileManagementStepDefinition {

    @Steps
    ProfileManagementSteps profileManagementSteps;

    @Given("I want to create a user in the Gaji Gesa")
    public void create_a_user_in_gaji_gesa() {
        profileManagementSteps.createAUser();
    }

    @When("a user submit profile create request")
    public void submit_profile_request(){
        profileManagementSteps.submitProfileCreate();
    }

    @Then("profile is created")
    public void check_profile_created(){
        profileManagementSteps.checkProfileCreated();
    }
}
