package com.moraustin.WebDriverTest;

import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.*;

/**
 * austin_moran
 * @since 8/21/14 */

public class MrInterviewerInputs {
    private static final Map<String, List<String>> SELECTORS;

    static {
        SELECTORS = ImmutableMap.of("questionBoundaries", Arrays.asList("div[id^=question__]", "#inputs"));
    }

    public static void main(String... args) {
        WebDriver driver = new FirefoxDriver();

        Integer foundInputs = null;

        driver.get("http://localhost:9998/9c4661ae-a3e6-4ddf-9a6d-489ea1a0762b--1.html");
        List<WebElement> inputs;

        for (String s : SELECTORS.get("questionBoundaries")) {
            try {
                inputs = driver.findElements(By.cssSelector(s));

                if (inputs != null && inputs.size() > 0) {
                    foundInputs = inputs.size();
                    break;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        driver.quit();

        assert foundInputs != null && foundInputs == 2;
    }
}
