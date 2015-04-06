package com.moraustin.WebDriverTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class GoogleSuggest {

    private static final String TARGET_URL = "http://www.google.com/webhp?complete=1&hl=en";

    private static final int TIMEOUT = 5000;
    private static final By RESULTS_DIV = By.className("sbdd_a");
    private static final By SUGGESTIONS = By.xpath("//div[@class='sbqs_c']");
    private static final By RESULTS = By.id("rcnt");
    private static final By BODY = By.tagName("body");

    private final WebDriver driver;
    private final WebDriverWait driverWait;

    private GoogleSuggest() throws MalformedURLException {
        DesiredCapabilities capability = DesiredCapabilities.firefox();
        this.driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capability);
        this.driverWait = new WebDriverWait(this.driver, TIMEOUT / 1000);
    }

    public static void main(String[] args) throws Exception {

        GoogleSuggest suggest = new GoogleSuggest();

        // Go to the Google Suggest home page
        suggest.driver.get(TARGET_URL);

        // Enter the query string "Cheese"
        WebElement query = suggest.driver.findElement(By.name("q"));
        query.sendKeys("Cheese");

        suggest.until(ExpectedConditions.visibilityOfElementLocated(RESULTS_DIV));

        suggest.until((WebDriver input) -> input.findElements(SUGGESTIONS).size() > 0);

        List<WebElement> allSuggestions = suggest.driver.findElements(SUGGESTIONS);

        // List the suggestions
        for (WebElement suggestion : allSuggestions) {
            System.out.println(suggestion.getText());
        }

        suggest.until(ExpectedConditions.visibilityOfElementLocated(RESULTS));

        // Now list the visible text
        WebElement body = suggest.driver.findElement(BODY);
        System.out.println(body.getText());

        suggest.driver.quit();
    }

    private void until(final com.google.common.base.Predicate<WebDriver> isTrue) {
        driverWait.until(isTrue);
    }

    private void until(final ExpectedCondition<WebElement> isTrue) {
        driverWait.until(isTrue);
    }
}

