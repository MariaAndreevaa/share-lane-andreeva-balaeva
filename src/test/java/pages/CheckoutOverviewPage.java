package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

/** Шаг 2 оформления заказа Swag Labs (/checkout-step-two.html) — сводка заказа. */
public class CheckoutOverviewPage extends BasePage {

    private static final By ITEM_NAME = By.cssSelector("[data-test=inventory-item-name]");
    private static final By SUBTOTAL_LABEL = By.cssSelector("[data-test=subtotal-label]");
    private static final By TAX_LABEL = By.cssSelector("[data-test=tax-label]");
    private static final By TOTAL_LABEL = By.cssSelector("[data-test=total-label]");
    private static final By FINISH_BUTTON = By.id("finish");

    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
        waitForVisible(TOTAL_LABEL);
    }

    public List<String> getItemNames() {
        return textsOfAll(ITEM_NAME);
    }

    /** Сумма товаров, из подписи вида «Item total: $29.99». */
    public double getSubtotalAmount() {
        return parseAmount(textOf(SUBTOTAL_LABEL));
    }

    /** Налог, из подписи вида «Tax: $2.40». */
    public double getTaxAmount() {
        return parseAmount(textOf(TAX_LABEL));
    }

    /** Итоговая сумма, из подписи вида «Total: $32.39». */
    public double getTotalAmount() {
        return parseAmount(textOf(TOTAL_LABEL));
    }

    public CheckoutCompletePage finishOrder() {
        clickAndWaitForEffect(FINISH_BUTTON, d -> d.getCurrentUrl().contains("/checkout-complete.html"));
        return new CheckoutCompletePage(driver);
    }

    private double parseAmount(String label) {
        return Double.parseDouble(label.replaceAll("[^0-9.]", ""));
    }
}
