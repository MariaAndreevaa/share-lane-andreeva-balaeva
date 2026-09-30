package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/** Шаг 1 оформления заказа Swag Labs (/checkout-step-one.html) — данные покупателя. */
public class CheckoutPage extends BasePage {

    private static final By FIRST_NAME_FIELD = By.id("first-name");
    private static final By LAST_NAME_FIELD = By.id("last-name");
    private static final By POSTAL_CODE_FIELD = By.id("postal-code");
    private static final By CONTINUE_BUTTON = By.id("continue");
    private static final By ERROR_MESSAGE = By.cssSelector("h3[data-test=error]");

    public CheckoutPage(WebDriver driver) {
        super(driver);
        waitForVisible(FIRST_NAME_FIELD);
    }

    public void fillCustomerInfo(String firstName, String lastName, String postalCode) {
        type(FIRST_NAME_FIELD, firstName);
        type(LAST_NAME_FIELD, lastName);
        type(POSTAL_CODE_FIELD, postalCode);
    }

    /** Переходит на шаг Overview (поля заполняются заранее методом {@link #fillCustomerInfo}). */
    public CheckoutOverviewPage continueToOverview() {
        clickAndWaitForEffect(CONTINUE_BUTTON, d -> d.getCurrentUrl().contains("/checkout-step-two.html"));
        return new CheckoutOverviewPage(driver);
    }

    /** Нажимает Continue без ожидания перехода — используется для проверки валидации:
     *  при ошибке остаёмся на шаге 1, и сообщение читается методом {@link #getErrorMessage()}. */
    public void clickContinueExpectingError() {
        click(CONTINUE_BUTTON);
        waitForVisible(ERROR_MESSAGE);
    }

    public String getErrorMessage() {
        return textOf(ERROR_MESSAGE);
    }
}
