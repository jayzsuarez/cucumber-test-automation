package steps.stepdefinitions.login;

import io.cucumber.java.en.Given;
import net.thucydides.core.annotations.Steps;
import steps.navigation.LoginSteps;

public class LoginStepDefinitions {

    @Steps
    LoginSteps loginSteps;

    @Given("^User successfully login to e-commerce. Username: (.*) and password: (.*)$")
    public void log_in_to_ecommerce(String username, String password){
        loginSteps.launchBrowser("https://www.saucedemo.com/");
        loginSteps.logInToSauceDemo(username, password);
    }

}
