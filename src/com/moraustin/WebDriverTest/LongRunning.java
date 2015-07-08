package com.moraustin.WebDriverTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LongRunning {

    private static final int MAX_THREADS = 2;
    private static final String SELENIUM_HUB_IP = "54.175.246.47";

    public static void main(String... args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);

        for (int i = 0; i < MAX_THREADS; i++) {

            try {
                RemoteWebDriver driver = new RemoteWebDriver(
                        new URL(String.format("http://%s:4444/wd/hub", SELENIUM_HUB_IP)),
                        DesiredCapabilities.firefox());
//                WebDriver driver = new FirefoxDriver();
                Runnable longTask = new LongTask(driver, i);
                executor.execute(longTask);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
    }

    private static class LongTask implements Runnable {
        private WebDriver driver;
        private int callCount;

        public LongTask(WebDriver driver, int callCount) {
            this.driver = driver;
            this.callCount = callCount;
        }

        @Override
        public void run() {
            try {
                BibleGetter2 bibleGetter2 = new BibleGetter2(driver);
                bibleGetter2.printAllBooks(System.out);
                System.out.printf("DONE CALL #%d\n", (callCount + 1));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
