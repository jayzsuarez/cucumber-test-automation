package steps.navigation;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import utils.BaseRunner;
import utils.FileUtil;
import utils.WebUtil;

public class LoginSteps extends BaseRunner {

    @Steps
    WebUtil webUtil;

    @Step
    public void launchBrowser(String url) {
        setDefaultBaseUrl(url);
        open();
    }

    @Step
    public void logInToSauceDemo(String username, String password){
        webUtil.doActionType(FileUtil.getXPathAndUpdate("input.id", "user-name"), username);
        webUtil.doActionType(FileUtil.getXPathAndUpdate("input.id", "password"), password);
        webUtil.doActionClick(FileUtil.getXPathAndUpdate("input.id", "login-button"));
    }
}
