package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CheckoutCompletePage;
import pages.CheckoutOverviewPage;
import pages.CheckoutPage;
import pages.InventoryPage;
import pages.LoginPage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

/**
 * Контрольные снимки ключевых экранов Swag Labs для отчёта по практической работе.
 * Не входит в основной набор (имя не подпадает под маски surefire *Test/*Tests)
 * и запускается отдельно: mvn test -Dtest=SauceDemoScreenshots
 */
public class SauceDemoScreenshots {

    private static final Path SCREENSHOT_DIR = Paths.get("screenshots");

    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--disable-features=PasswordLeakDetection");
        driver = new ChromeDriver(options);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test(description = "Обход ключевых экранов с сохранением снимков в screenshots/")
    public void captureKeyScreens() throws IOException {
        Files.createDirectories(SCREENSHOT_DIR);

        // 1. Успешный вход и каталог товаров
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(SauceDemoConfig.STANDARD_USER, SauceDemoConfig.PASSWORD);
        InventoryPage catalog = new InventoryPage(driver);
        catalog.addItemToCart(InventoryPage.ITEM_BACKPACK);
        saveScreen("01-inventory-products.png");

        // 2. Корзина с добавленным товаром
        CartPage cart = catalog.openCart();
        saveScreen("02-cart.png");

        // 3. Данные покупателя
        CheckoutPage form = cart.startCheckout();
        form.fillCustomerInfo("Ivan", "Petrov", "101000");
        saveScreen("03-checkout-info.png");

        // 4. Сводка заказа
        CheckoutOverviewPage overview = form.continueToOverview();
        saveScreen("04-checkout-overview.png");

        // 5. Подтверждение заказа
        CheckoutCompletePage complete = overview.finishOrder();
        saveScreen("05-checkout-complete.png");

        // 6. Ошибка входа заблокированного пользователя (свежая сессия)
        driver.quit();
        setUp();
        LoginPage lockedLogin = new LoginPage(driver);
        lockedLogin.login(SauceDemoConfig.LOCKED_OUT_USER, SauceDemoConfig.PASSWORD);
        lockedLogin.getErrorMessage();
        saveScreen("06-login-locked-out-error.png");
    }

    private void saveScreen(String fileName) throws IOException {
        var source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Files.copy(source.toPath(), SCREENSHOT_DIR.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
    }
}
