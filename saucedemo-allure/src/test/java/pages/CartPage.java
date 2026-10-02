package pages;

import base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage extends BasePage {

    private final By cartItem = By.cssSelector(".cart_item");
    private final By itemName = By.cssSelector(".inventory_item_name");
    private final By itemPrice = By.cssSelector(".inventory_item_price");
    private final By removeBackpackButton = By.id("remove-sauce-labs-backpack");
    private final By checkoutButton = By.id("checkout");
    private final By cartBadge = By.cssSelector(".shopping_cart_badge");

    public CartPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Step("Прочитать название товара в корзине")
    public String getItemName() {
        return el(itemName).getText();
    }

    @Step("Прочитать цену товара в корзине")
    public String getItemPrice() {
        return el(itemPrice).getText();
    }

    @Step("Удалить Sauce Labs Backpack из корзины")
    public void removeBackpack() {
        click(removeBackpackButton);
    }

    @Step("Дождаться, что корзина станет пустой")
    public void waitForCartEmpty() {
        wait.until(ExpectedConditions.numberOfElementsToBe(cartItem, 0));
    }

    @Step("Дождаться исчезновения счётчика корзины")
    public void waitForCartBadgeDisappears() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(cartBadge));
    }

    @Step("Нажать Checkout")
    public CheckoutPage clickCheckout() {
        click(checkoutButton);
        return new CheckoutPage(driver, wait);
    }
}