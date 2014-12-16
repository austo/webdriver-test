package com.moraustin.WebDriverTest;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.moraustin.WebDriverTest.tools.TextFinder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextExtractor {
    public static void main(String[] args) {
        // Create a new instance of the html unit driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.
        WebDriver driver = new ChromeDriver();

        driver.get("http://survey.euro.confirmit.com/wix0/p1662438240.aspx");

        String bodyText = driver.findElement(By.xpath("//html/body")).getText().toLowerCase();

        System.out.println("Page text:");
        for (String s : Splitter.on('\n').omitEmptyStrings().split(bodyText)) {
            TextFinder.Result result = TextFinder.isQuestion(s);
            if (result.found) {
                System.out.printf("%s (Question about %s)\n",
                        s, Joiner.on(", ").skipNulls().join(result.subjects));
            }
            else {
                System.out.printf("%s\n", s);
            }
        }
        driver.quit();
    }

}
