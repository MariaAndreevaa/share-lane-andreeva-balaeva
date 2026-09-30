package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Каталог товаров Swag Labs (/inventory.html).
 * Кнопки товаров имеют стабильные id вида add-to-cart-{itemId} / remove-{itemId}.
 */
public class InventoryPage extends BasePage {

    // Значения id товаров без служебного префикса add-to-cart- / remove-
    public static final String ITEM_BACKPACK = "sauce-labs-backpack";
    public static final String ITEM_BIKE_LIGHT = "sauce-labs-bike-light";

    private static final By CATALOG_TITLE = By.cssSelector("[data-test=title]");
    private static final By CATALOG_LIST = By.cssSelector("[data-test=inventory-list]");
    private static final By ITEM_NAME = By.cssSelector("[data-test=inventory-item-name]");
    private static final By ITEM_PRICE = By.cssSelector("[data-test=inventory-item-price]");
    private static final By CART_LINK = By.cssSelector("[data-test=shopping-cart-link]");
    private static final By CART_BADGE = By.cssSelector("[data-test=shopping-cart-badge]");
    private static final By SORT_DROPDOWN = By.cssSelector("[data-test=product-sort-container]");

    public InventoryPage(WebDriver driver) {
        super(driver);
        waitForUrlContains("/inventory.html");
        waitForVisible(CATALOG_LIST);
    }

    public String getCatalogTitle() {
        return textOf(CATALOG_TITLE);
    }

    /** Добавляет товар в корзину и дожидается увеличения счётчика —
     *  React обновляет бейдж асинхронно после клика. */
    public void addItemToCart(String itemId) {
        int countBefore = readCartBadgeCount();
        clickAndWaitForEffect(By.id("add-to-cart-" + itemId), d -> readCartBadgeCount() == countBefore + 1);
    }

    /** Удаляет товар из корзины и дожидается уменьшения счётчика. */
    public void removeItemFromCart(String itemId) {
        int countBefore = readCartBadgeCount();
        clickAndWaitForEffect(By.id("remove-" + itemId), d -> readCartBadgeCount() == countBefore - 1);
    }

    /** Количество товаров в корзине; при пустой корзине бейдж не отображается — 0. */
    public int getCartBadgeCount() {
        return readCartBadgeCount();
    }

    private int readCartBadgeCount() {
        List<WebElement> badges = driver.findElements(CART_BADGE);
        return badges.isEmpty() ? 0 : Integer.parseInt(badges.get(0).getText());
    }

    public List<String> getItemNames() {
        return textsOfAll(ITEM_NAME);
    }

    public List<Double> getItemPrices() {
        return textsOfAll(ITEM_PRICE).stream()
                .map(price -> Double.parseDouble(price.replace("$", "")))
                .collect(Collectors.toList());
    }

    public void sortBy(SortOption option) {
        waitForVisible(SORT_DROPDOWN);
        new Select(driver.findElement(SORT_DROPDOWN)).selectByValue(option.value());
    }

    public CartPage openCart() {
        clickAndWaitForEffect(CART_LINK, d -> d.getCurrentUrl().contains("/cart.html"));
        return new CartPage(driver);
    }
}
