package pages;

import base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class InventoryPage extends BasePage {

    private final By pageTitle = By.cssSelector("[data-test='title']");
    private final By backpackAddButton = By.id("add-to-cart-sauce-labs-backpack");
    private final By cartBadge = By.cssSelector(".shopping_cart_badge");
    private final By cartLink = By.cssSelector("a.shopping_cart_link");
    private final By sortDropdown = By.cssSelector("select.product_sort_container");
    private final By firstItemName = By.cssSelector(".inventory_item_name");
    private final By itemPrices = By.cssSelector(".inventory_item_price");

    public InventoryPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Step("Прочитать заголовок каталога")
    public String getTitleText() {
        return el(pageTitle).getText();
    }

    @Step("Добавить Sauce Labs Backpack в корзину")
    public void addBackpackToCart() {
        click(backpackAddButton);
    }

    @Step("Дождаться значения счётчика корзины")
    public void waitForCartBadgeText(String expected) {
        wait.until(ExpectedConditions.textToBe(cartBadge, expected));
    }

    @Step("Прочитать значение счётчика корзины")
    public String getCartBadgeText() {
        return el(cartBadge).getText();
    }

    @Step("Открыть корзину")
    public CartPage openCart() {
        click(cartLink);
        return new CartPage(driver, wait);
    }

    @Step("Установить сортировку Price (low to high)")
    public void sortByPriceLowToHigh() {
        new Select(el(sortDropdown)).selectByValue("lohi");
    }

    @Step("Дождаться, что первый товар в списке обновится")
    public void waitForFirstItemName(String expected) {
        wait.until(ExpectedConditions.textToBe(firstItemName, expected));
    }

    @Step("Прочитать название первого товара")
    public String getFirstItemName() {
        return el(firstItemName).getText();
    }

    @Step("Прочитать цену первого товара")
    public String getFirstItemPrice() {
        return el(itemPrices).getText();
    }

    @Step("Собрать цены всех товаров каталога")
    public List<Double> getItemPricesAsNumbers() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(itemPrices));
        List<Double> prices = new ArrayList<>();
        for (WebElement price : driver.findElements(itemPrices)) {
            prices.add(Double.parseDouble(price.getText().replace("$", "")));
        }
        return prices;
    }
}