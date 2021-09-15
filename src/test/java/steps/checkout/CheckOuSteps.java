package steps.checkout;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;
import utils.FileUtil;
import utils.WebUtil;

public class CheckOuSteps {

    @Steps
    WebUtil webUtil;

    @Step
    public void proceedToCheckOut() {
        webUtil.doActionClick(FileUtil.getXPathAndUpdate("a.class","shopping_cart_link"));
        webUtil.doActionClick(FileUtil.getXPathAndUpdate("button.text","Checkout"));
        webUtil.doActionType(FileUtil.getXPathAndUpdate("input.id", "first-name"), "Juan");
        webUtil.doActionType(FileUtil.getXPathAndUpdate("input.id", "last-name"), "Dela Cruz");
        webUtil.doActionType(FileUtil.getXPathAndUpdate("input.id", "postal-code"), "Dela Cruz");
        webUtil.doActionClick(FileUtil.getXPathAndUpdate("input.id", "continue"));
        webUtil.scrollPageDown();
        webUtil.doActionClick(FileUtil.getXPathAndUpdate("button.text", "Finish"));
    }

    @Step
    public void completeOrder() {
        webUtil.scrollPageUp();
        String completeOrder = webUtil.doActionGetText(FileUtil.getXPathAndUpdate("h2.class","complete-header"));
        Assert.assertEquals(completeOrder, "THANK YOU FOR YOUR ORDER");
    }
}
