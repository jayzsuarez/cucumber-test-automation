package steps.products;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;
import utils.FileUtil;
import utils.LogUtil;
import utils.WebUtil;

public class ProductScreenSteps {
    @Steps
    WebUtil webUtil;

    @Step
    public void validatePriceTagOfItem(String itemName, String priceValue) {
        String price = webUtil.doActionGetText(FileUtil.getXPathAndUpdate("product.name.price", itemName));
        LogUtil.info(this, String.format("Price of item [%s] is [%s]", itemName, price));
        Assert.assertEquals(price, priceValue);
    }

    @Step
    public void validateUserIsInProductScreen(){
        boolean isProductDisplayed = webUtil.isElementExist(FileUtil.getXPathAndUpdate("span.text","Products"));
        Assert.assertTrue(isProductDisplayed);
    }

    @Step
    public void addItemInTheCart(String itemName) {
        webUtil.doActionClick(FileUtil.getXPathAndUpdate("product.add.cart.name",itemName));
    }
}
