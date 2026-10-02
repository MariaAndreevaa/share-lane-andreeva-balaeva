package base;

import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utils.AllureAttachments;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

public abstract class BaseTest {

    protected static final String BASE_URL = "https://www.saucedemo.com/";

    protected WebDriver driver;
    protected WebDriverWait wait;

    private static String browserUsed = "chrome";

    @Parameters({"browser", "headless"})
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser,
                      @Optional("false") String headless) {
        // Системные свойства Maven имеют приоритет над параметрами testng.xml/@Optional
        browser = System.getProperty("browser", browser);
        headless = System.getProperty("headless", headless);

        browserUsed = browser;
        driver = createDriver(browser, Boolean.parseBoolean(headless));
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Метка браузера — видна в карточке каждого теста в Allure-отчёте
        Allure.label("browser", browserUsed);

        driver.get(BASE_URL);
    }

    private WebDriver createDriver(String browser, boolean headless) {
        switch (browser.toLowerCase()) {
            case "firefox" -> {
                FirefoxOptions fo = new FirefoxOptions();
                if (headless) fo.addArguments("-headless");
                return new FirefoxDriver(fo);
            }
            case "edge" -> {
                EdgeOptions eo = new EdgeOptions();
                if (headless) eo.addArguments("--headless=new");
                return new EdgeDriver(eo);
            }
            default -> {
                ChromeOptions co = new ChromeOptions();
                if (headless) co.addArguments("--headless=new");
                return new ChromeDriver(co);
            }
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (driver != null && !result.isSuccess()) {
            AllureAttachments.screenshot(driver, "Failure screenshot");
            AllureAttachments.pageSource(driver, "Page source on failure");
        }
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite(alwaysRun = true)
    public void writeAllureEnvironment() {
        try {
            Path resultsDir = Path.of("target", "allure-results");
            Files.createDirectories(resultsDir);
            String content = String.join(System.lineSeparator(),
                    "os.name=" + System.getProperty("os.name"),
                    "jdk.version=" + System.getProperty("java.version"),
                    "selenium=4.27.0",
                    "testng=7.10.2",
                    "browser=" + browserUsed,
                    "driver.manager=SeleniumManager")
                    + System.lineSeparator();
            Files.writeString(resultsDir.resolve("environment.properties"), content);
        } catch (IOException e) {
            System.err.println("Не удалось записать environment.properties: " + e.getMessage());
        }
    }
}