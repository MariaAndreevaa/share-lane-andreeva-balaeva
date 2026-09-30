package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/** Финальная страница оформления заказа Swag Labs (/checkout-complete.html). */
public class CheckoutCompletePage extends BasePage {

    private static final By COMPLETE_HEADER = By.cssSelector("[data-test=complete-header]");
    private static final By COMPLETE_TEXT = By.cssSelector("[data-test=complete-text]");

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
        waitForVisible(COMPLETE_HEADER);
    }

    public String getCompleteHeaderText() {
        return textOf(COMPLETE_HEADER);
    }

    public String getCompleteText() {
        return textOf(COMPLETE_TEXT);
    }
}
