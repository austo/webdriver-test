package com.moraustin.WebDriverTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;


public class BibleGetter {
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

  private final WebDriver driver;
  private final WebDriverWait driverWait;

  private BibleGetter() {
    this.driver = new FirefoxDriver();
    this.driverWait = new WebDriverWait(this.driver, TIMEOUT / 1000);
  }

  public static void main(String[] args) throws Exception {

    BibleGetter getter = new BibleGetter();

    // Go to the Google Suggest home page
    getter.driver.get(BOOK_LIST_URL);

    getter.until((WebDriver input) -> input.findElements(BOOK_NAME).size() > 0);

    try {
      getter.printLinks();
//      for (By b : Arrays.asList(OT_BOOK, NT_BOOK, AP_BOOK)) {
//        List<WebElement> books = getter.driver.findElements(b);
//        books.forEach(getter::getBookText);
//      }
    }
    finally {
      getter.driver.quit();

    }
  }

  private void until(final com.google.common.base.Predicate<WebDriver> isTrue) {
    driverWait.until(isTrue);
  }

  private void getBookText(WebElement otBook) {
    String bookName = otBook.findElement(BOOK_NAME).getText();
    System.out.printf("%s:\n", bookName);
    List<WebElement> chapterLinks = otBook.findElement(CHAPTERS).findElements(By.tagName("a"));

    for (WebElement chapterLink : chapterLinks) {
      traverseChapter(chapterLink);

    }
  }

  private void traverseChapter(WebElement chapterLink) {
    System.out.printf("%s\n", chapterLink.getText());
    chapterLink.click();
    driverWait.until(ExpectedConditions.presenceOfElementLocated(PASSAGE_TEXT));
    WebElement passageText = driver.findElement(PASSAGE_TEXT);
    System.out.println(passageText.getText());
    driver.navigate().back();
    until((WebDriver input) -> input.findElements(BOOK_NAME).size() > 0);
  }

  private WebElement getChapterLink(By testament, String bookName, String chapterName) {
    until(d -> d.findElements(BOOK_NAME).size() > 0);
    List<WebElement> books = driver.findElements(testament);
    for (WebElement book : books) {
      WebElement bookRow = book.findElement(BOOK_NAME);
      if (!bookRow.getText().equals(bookName)) {
        continue;
      }
      List<WebElement> chapterLinks = bookRow.findElement(CHAPTERS).findElements(By.tagName("a"));
      for (WebElement chapterLink : chapterLinks) {
        if (!chapterLink.getText().equals(chapterName)) {
          continue;
        }
        return chapterLink;
      }
    }
    return null;
  }

  private void printLinks() {
    List<WebElement> links = driver.findElements(By.tagName("a"));
    for (WebElement link : links) {
      String linkText = link.getText();
      int linkIndex;
      try {
        linkIndex = Integer.parseInt(linkText);
        if (linkIndex > 0 && linkIndex < 200) {
          System.out.printf("%s%s\n", BASE_URL, link.getAttribute("href"));
        }
      } catch (NumberFormatException ignored) {
      }
    }
  }
}
