package base;

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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utils.AllureAttachments;

import java.time.Duration;

public abstract class BaseTest {

    protected static final String BASE_URL = "https://www.saucedemo.com/";

    protected WebDriver driver;
    protected WebDriverWait wait;

    @Parameters({"browser", "headless"})
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser,
                      @Optional("false") String headless) {
        driver = createDriver(browser, Boolean.parseBoolean(headless));
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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
}