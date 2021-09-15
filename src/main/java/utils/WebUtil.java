package utils;

import net.serenitybdd.core.pages.WebElementFacade;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.temporal.ChronoUnit;

public class WebUtil extends BaseRunner{

    public boolean doActionType(String xPath, String text, Keys... keys) {

        WebElement element = doWaitForElement(xPath, 30);
        boolean result = element != null;
        if (result) {
            if (StringUtils.isNotEmpty(text)) {
                element.sendKeys(text);
            }
            if (keys.length > 0) {
                result = doActionSendKey(xPath, keys);
            }
        }
        return result;
    }

    public WebElement doWaitForElement(String xPath, int sec) {
        WebDriverWait wait = new WebDriverWait(getDriver(), sec);

        WebElement element = null;

        try {
            element = (wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xPath))));
            LogUtil.info(this, element.toString());
        } catch (TimeoutException | NoSuchElementException e) {
            LogUtil.warn(this, "Error, " + e);
        }

        return element;
    }

    public boolean doActionSendKey(String xPath, Keys... keys) {
        WebElement e = doWaitForElement(xPath);
        boolean result = e != null;
        if (e != null)
            e.sendKeys(keys);
        return result;
    }

    public WebElement doWaitForElement(String xPath) {
        return doWaitForElement(xPath, 10);
    }

    public boolean doActionClick(String xPath) {
        WebElement e = doWaitForElement(xPath);
        boolean result = e != null;
        try {
            if (e != null)
                e.click();
        } catch (StaleElementReferenceException staleElementReferenceException) {
            doWaitForElement(xPath, 15).click();
        }

        return result;
    }

    public String doActionGetText(String xPath) {
        String result;
        try {
            result = doActionGetText(xPath, 10);
        } catch (NoSuchElementException noSuchElementException) {
            LogUtil.warn(this, xPath + " is not found" + noSuchElementException);
            result = "Element not found!";
        }
        return result;
    }

    public String doActionGetText(String xPath, int sec) {
        String result = "";
        WebElement e = doWaitForElement(xPath, sec);
        try {
            if (e != null) result = e.getText();
        } catch (NoSuchElementException noSuchElementException) {
            LogUtil.warn(this, xPath + " is not found" + noSuchElementException);
            result = "Element not found!";
        }

        return result;
    }

    public boolean isElementExist(String xPath) {
        return isElementExist(xPath, 3);
    }

    public boolean isElementExist(String xPath, int sec) {
        boolean result;
        try {
            result = doActionCheckElementExist(xPath, sec);
        } catch (NoSuchElementException e) {
            LogUtil.warn(this, e.getMessage());
            result = false;
        }
        return result;
    }

    public boolean doActionCheckElementExist(String xPath, int sec) {
        return doWaitUntilPresent(find(new By.ByXPath(xPath)), sec);
    }

    public boolean doWaitUntilPresent(WebElementFacade element, int sec) {
        setImplicitTimeout(1, ChronoUnit.SECONDS);

        int i = 0;
        boolean result = false;
        while (!result & i < sec) {
            result = element.isPresent();
            i++;
        }

        resetImplicitTimeout();
        return result;
    }

    public void scrollPageDown() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0,1000)");
    }

    public void scrollPageUp() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
    }
}
