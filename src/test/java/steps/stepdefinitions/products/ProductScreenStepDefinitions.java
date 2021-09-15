package steps.stepdefinitions.products;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import net.thucydides.core.annotations.Steps;
import steps.products.ProductScreenSteps;

public class ProductScreenStepDefinitions {
    @Steps
    ProductScreenSteps  productScreenSteps;

    @Then("^User can verify that item (.*) has a price tag of (.*)$")
    public void verifyPriceValueOfItem(String itemName, String priceValue) {
        productScreenSteps.validatePriceTagOfItem(itemName, priceValue);
    }

    @Then("User is in the products screen")
    public void verifyUserInProductScreen(){
        productScreenSteps.validateUserIsInProductScreen();
    }

    @Given("^User add to cart item (.*)$")
    public void userAddItemInTheCart(String itemName){
        productScreenSteps.addItemInTheCart(itemName);
    }
}
