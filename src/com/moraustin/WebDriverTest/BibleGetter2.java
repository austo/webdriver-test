package com.moraustin.WebDriverTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class BibleGetter2 {
    private static final String BASE_URL = "https://www.biblegateway.com";

    private static final String BOOK_LIST_URL = BASE_URL +
            "/versions/New-Revised-Standard-Version-NRSV-Bible/#booklist";

    private static final int TIMEOUT = 5000;
    private static final By BOOK_NAME = By.className("book-name");
    private static final By OT_BOOK = By.className("ot-book");
    private static final By NT_BOOK = By.className("nt-book");
    private static final By AP_BOOK = By.className("ap-book");
    private static final By CHAPTERS = By.className("chapters");
    private static final By PASSAGE_TEXT = By.className("passage-text");
    private static final By VERSE_REGION = By.className("span[id^=en-NRSV-]");

    private final WebDriver driver;
    private final WebDriverWait driverWait;

    public BibleGetter2(WebDriver driver) {
        this.driver = driver;
        this.driverWait = new WebDriverWait(this.driver, TIMEOUT / 1000);
    }

    public static void main(String[] args) throws Exception {
        BibleGetter2 getter = new BibleGetter2(new FirefoxDriver());

        try {
//            getter.printAllBooks(System.out);
            getter.printBook("Exodus", System.out);
        } finally {
            getter.driver.quit();
        }
    }

    public void printAllBooks(PrintStream printStream) {
        for (By by : Arrays.asList(OT_BOOK, NT_BOOK, AP_BOOK)) {
            driver.get(BOOK_LIST_URL);
            driverWait.until((WebDriver input) -> input.findElements(by).size() > 0);

            List<WebElement> elements = driver.findElements(by);

            for (int i = 0; i < elements.size(); i++) {
                printStream.println(getBookText(by, i));
            }
        }
    }

    public void printBookNames(PrintStream printStream) {
        driver.get(BOOK_LIST_URL);
        driverWait.until((WebDriver d) -> d.findElements(BOOK_NAME).size() > 0);

        List<WebElement> elements = driver.findElements(BOOK_NAME);

        elements.forEach(e -> printStream.println(e.getText()));
    }

    public void printBook(String bookName, PrintStream printStream) {
        driver.get(BOOK_LIST_URL);
        driverWait.until((WebDriver input) -> input.findElements(BOOK_NAME).size() > 0);

        for (By by : Arrays.asList(OT_BOOK, NT_BOOK, AP_BOOK)) {
            List<WebElement> elements = driver.findElements(by);

            for (int i = 0; i < elements.size(); i++) {
                WebElement element = elements.get(i);
                if (element.findElement(BOOK_NAME).getText().equalsIgnoreCase(bookName)) {
                    printStream.println(getBookText(by, i));
                    return;
                }
            }
        }
    }

    private String getBookText(By by, int index) {
        List<String> links = getBookLinks(by, index);
        StringBuilder builder = new StringBuilder();
        links.stream().map(this::getChapterText).forEach(s -> {
            builder.append(s).append("\n\n");
        });
        return builder.toString();
    }

    private List<String> getBookLinks(By by, int index) {
        driver.get(BOOK_LIST_URL);
        driverWait.until((WebDriver input) -> input.findElements(by).size() > 0);

        List<WebElement> elements = driver.findElements(by);

        for (int i = 0; i < elements.size(); i++) {
            if (i != index) {
                continue;
            }
            WebElement element = elements.get(i);
            return element.findElement(CHAPTERS).findElements(By.tagName("a")).stream().map(e ->
                    e.getAttribute("href")).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private String getChapterText(String chapterUrl) {
        driver.get(chapterUrl);
        driverWait.until(ExpectedConditions.presenceOfElementLocated(PASSAGE_TEXT));
        WebElement passageText = driver.findElement(PASSAGE_TEXT);
        return passageText.getText();
    }
}
