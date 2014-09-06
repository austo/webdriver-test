package com.moraustin.WebDriverTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

public class GoogleSuggest {
  private static final int TIMEOUT = 5000;
  private static final String RESULTS_DIV = "sbdd_a";
  private static final String SUGGESTIONS = "//div[@class='sbqs_c']";

  private final WebDriver driver;

  private GoogleSuggest() {
    this.driver = new FirefoxDriver();
  }

  public static void main(String[] args) throws Exception {

    GoogleSuggest suggest = new GoogleSuggest();

    // Go to the Google Suggest home page
    suggest.driver.get("http://www.google.com/webhp?complete=1&hl=en");


    // Enter the query string "Cheese"
    WebElement query = suggest.waitForDisplayedElement(By.name("q"), TIMEOUT);
    query.sendKeys("Cheese");

    Thread.sleep(500);

    suggest.waitForDisplayedElement(By.className(RESULTS_DIV), TIMEOUT);

    // And now list the suggestions

    List<WebElement> allSuggestions = suggest.waitForDisplayedElements(By.xpath(SUGGESTIONS), TIMEOUT);

    for (WebElement suggestion : allSuggestions) {
      System.out.println(suggestion.getText());
    }

    Thread.sleep(500);

    // Now list the visible text
    WebElement body = suggest.driver.findElement(By.tagName("body"));
    System.out.println(body.getText());

    suggest.driver.quit();
  }

  private WebElement waitForDisplayedElement(By by, int timeout) {
    long end = System.currentTimeMillis() + timeout;
    while (System.currentTimeMillis() < end) {
      WebElement retval = driver.findElement(by);

      // If results have been returned, the results are displayed in a drop down.
      if (retval.isDisplayed()) {
        return retval;
      }
    }
    return null;
  }

  private List<WebElement> waitForDisplayedElements(By by, int timeout) {
    long end = System.currentTimeMillis() + timeout;
    while (System.currentTimeMillis() < end) {
      List<WebElement> retval = driver.findElements(by);

      // If results have been returned, the results are displayed in a drop down.
      if (retval != null) {
        return retval;
      }
    }
    return null;
  }
}

