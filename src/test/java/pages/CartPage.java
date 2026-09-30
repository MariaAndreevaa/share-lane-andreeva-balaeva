package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.stream.Collectors;

/** Корзина Swag Labs (/cart.html). */
public class CartPage extends BasePage {

    private static final By CART_LIST = By.cssSelector("[data-test=cart-list]");
    private static final By ITEM_NAME = By.cssSelector("[data-test=inventory-item-name]");
    private static final By ITEM_PRICE = By.cssSelector("[data-test=inventory-item-price]");
    private static final By CHECKOUT_BUTTON = By.id("checkout");

    public CartPage(WebDriver driver) {
        super(driver);
        waitForVisible(CART_LIST);
    }

    public List<String> getItemNames() {
        return textsOfAll(ITEM_NAME);
    }

    public List<Double> getItemPrices() {
        return textsOfAll(ITEM_PRICE).stream()
                .map(price -> Double.parseDouble(price.replace("$", "")))
                .collect(Collectors.toList());
    }

    public CheckoutPage startCheckout() {
        clickAndWaitForEffect(CHECKOUT_BUTTON, d -> d.getCurrentUrl().contains("/checkout-step-one.html"));
        return new CheckoutPage(driver);
    }
}
