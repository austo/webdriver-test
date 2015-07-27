package com.moraustin.WebDriverTest.tools;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class FlashTest {

    private static final String SELENIUM_HUB_IP = "54.175.117.207";

    public static void main(String... args) throws InterruptedException, IOException {
        RemoteWebDriver driver = new RemoteWebDriver(
                new URL(String.format("http://%s:4444/wd/hub", SELENIUM_HUB_IP)),
                DesiredCapabilities.firefox());

        driver.get("http://www.adobe.com/software/flash/about/");

        Thread.sleep(1500);

        final byte[] screenshotBytes = driver.getScreenshotAs(OutputType.BYTES);

        FileOutputStream fileOutputStream = new FileOutputStream("flashTest.png");
        fileOutputStream.write(screenshotBytes);
        fileOutputStream.close();

        driver.quit();
    }
}
