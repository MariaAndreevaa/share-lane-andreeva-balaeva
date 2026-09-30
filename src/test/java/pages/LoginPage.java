package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.SauceDemoConfig;

/**
 * Страница входа Swag Labs (/).
 * Поля и кнопка имеют стабильные id (#user-name, #password, #login-button).
 */
public class LoginPage extends BasePage {

    private static final By USERNAME_FIELD = By.id("user-name");
    private static final By PASSWORD_FIELD = By.id("password");
    private static final By LOGIN_BUTTON = By.id("login-button");
    private static final By ERROR_MESSAGE = By.cssSelector("h3[data-test=error]");

    public LoginPage(WebDriver driver) {
        super(driver);
        driver.get(SauceDemoConfig.BASE_URL);
        waitForVisible(USERNAME_FIELD);
    }

    /** Входит с переданными учётными данными. При успехе тест создаёт InventoryPage —
     *  его конструктор дожидается загрузки каталога. */
    public void login(String username, String password) {
        type(USERNAME_FIELD, username);
        type(PASSWORD_FIELD, password);
        click(LOGIN_BUTTON);
    }

    public String getErrorMessage() {
        return textOf(ERROR_MESSAGE);
    }
}
