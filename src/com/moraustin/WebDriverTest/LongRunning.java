package com.moraustin.WebDriverTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LongRunning {


    private static final String[] BOOKS = {
            "Genesis",
            "Exodus",
            "Leviticus",
            "Numbers",
            "Deuteronomy",
            "Joshua",
            "Judges",
            "Ruth",
            "1 Samuel",
            "2 Samuel",
            "1 Kings",
            "2 Kings",
            "1 Chronicles",
            "2 Chronicles",
            "Ezra",
            "Nehemiah",
            "Esther",
            "Job",
            "Psalm",
            "Proverbs",
            "Ecclesiastes",
            "Song of Solomon",
            "Isaiah",
            "Jeremiah",
            "Lamentations",
            "Ezekiel",
            "Daniel",
            "Hosea",
            "Joel",
            "Amos",
            "Obadiah",
            "Jonah",
            "Micah",
            "Nahum",
            "Habakkuk",
            "Zephaniah",
            "Haggai",
            "Zechariah",
            "Malachi",
            "Matthew",
            "Mark",
            "Luke",
            "John",
            "Acts",
            "Romans",
            "1 Corinthians",
            "2 Corinthians",
            "Galatians",
            "Ephesians",
            "Philippians",
            "Colossians",
            "1 Thessalonians",
            "2 Thessalonians",
            "1 Timothy",
            "2 Timothy",
            "Titus",
            "Philemon",
            "Hebrews",
            "James",
            "1 Peter",
            "2 Peter",
            "1 John",
            "2 John",
            "3 John",
            "Jude",
            "Revelation",
            "Tobit",
            "Judith",
            "Greek Esther",
            "Wisdom",
            "Sirach",
            "Baruch",
            "Letter of Jeremiah",
            "Prayer of Azariah",
            "Susanna",
            "Bel and the Dragon",
            "1 Maccabees",
            "2 Maccabees",
            "1 Esdras",
            "Prayer of Manasseh",
            "Psalm 151",
            "3 Maccabees",
            "2 Esdras",
            "4 Maccabees"
    };

    private static final String SELENIUM_HUB_IP = /*"54.175.246.47"; */ "54.175.117.207";

    public static void main(String... args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(BOOKS.length);

        for (int i = 0; i < BOOKS.length; i++) {

            try {
                RemoteWebDriver driver = new RemoteWebDriver(
                        new URL(String.format("http://%s:4444/wd/hub", SELENIUM_HUB_IP)),
                        DesiredCapabilities.firefox());
//                WebDriver driver = new FirefoxDriver();
                Runnable longTask = new LongTask(driver, BOOKS[i], i);
                executor.execute(longTask);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
    }

    private static class LongTask implements Runnable {
        private final WebDriver driver;
        private final int callCount;
        private final String bookName;

        public LongTask(WebDriver driver, String bookName, int callCount) {
            this.driver = driver;
            this.callCount = callCount;
            this.bookName = bookName;
        }

        @Override
        public void run() {
            try {
                BibleGetter2 bibleGetter2 = new BibleGetter2(driver);
                bibleGetter2.printBook(bookName, System.out);
                System.out.printf("DONE CALL #%d\n", (callCount + 1));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
