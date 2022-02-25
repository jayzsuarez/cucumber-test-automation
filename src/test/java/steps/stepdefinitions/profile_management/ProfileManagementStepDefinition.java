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
        profileManagementSteps.prepareProfileRequest();
    }

    @When("a user submit profile create request")
    public void submit_profile_request(){
        profileManagementSteps.submitProfileCreate();
    }

    @Then("profile is created")
    public void check_profile_created(){
        profileManagementSteps.checkProfileCreated();
    }

    @When("User updated profile")
    public void user_update_profile(){
        profileManagementSteps.submitUpdateProfile();
    }

    @Then("New profile detail is returned")
    public void updated_profile_returned(){
        profileManagementSteps.updatedProfileReturned();
    }

    @Given("^User retrieve specific user (.*)$")
    public void retrieve_specific_user(String id){
        profileManagementSteps.retrieveSpecificUser(id);
    }

    @When("^A user updated all profile details of user (.*)$")
    public void update_all_profile_details(String id){
        profileManagementSteps.updateAllUserDetails(id);
    }

    @Then("All new profile detail is returned")
    public void verify_all_new_profile_values(){
        profileManagementSteps.verifyAllNewProfileUpdate();
    }

    @When("^Updated all profile details with malformed request of user (.*)$")
    public void update_all_profile_with_malformed_details(String id){
        profileManagementSteps.updateAllUserDetailsWithMalformed(id);
    }

    @Then("Error malformed update is returned")
    public void verify_malformed_request_returned(){
        profileManagementSteps.verifyMalformedIsReturned();
    }

    @When("User submits delete  newly created user")
    public void submit_delete_user(){
        profileManagementSteps.submitDeleteUser();
    }


    @Then("Newly created user is deleted")
    public void verify_created_user_is_deleted(){
        profileManagementSteps.verifyUserIsDeleted();

    }
}
