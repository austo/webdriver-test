package com.moraustin.WebDriverTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.out;

public class Commuter {

    private static final By DURATION_SELECTOR = By.cssSelector(".cards-directions-body");
    private static final Pattern VIA_PATTERN = Pattern.compile("(^via.*?)(\\d+\\smin)", Pattern.CASE_INSENSITIVE);

    public static void main(String... args) throws InterruptedException {
        WebDriver driver = new FirefoxDriver();

        driver.get("https://www.google.com/maps/dir/6+Research+Dr,+Shelton,+CT+06484/550+Prospect+Street,+New+Haven,+CT+06511-2136,+USA/@41.2987318,-73.0975921,12z/data=!3m1!4b1!4m14!4m13!1m5!1m1!1s0x89e80af5f0695115:0xab578906fd9552bb!2m2!1d-73.1345168!2d41.275046!1m5!1m1!1s0x89e7d995306eb5b9:0x795a7a76dbff4b91!2m2!1d-72.923103!2d41.327096!3e0");
        Thread.sleep(4000);
        List<WebElement> elements = driver.findElements(DURATION_SELECTOR);
        for (WebElement element : elements) {
            Matcher matcher = VIA_PATTERN.matcher(element.getText());
            while (matcher.find()) {
                String route = matcher.group(1).trim();
                String time = matcher.group(2).trim();
                out.printf("%s | %s\n", route, time);
            }
        }
        driver.quit();
    }
}
