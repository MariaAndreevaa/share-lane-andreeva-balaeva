package tests;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.InventoryPage;
import pages.LoginPage;
import utils.Credentials;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@Epic("SauceDemo")
@Feature("Cart")
public class CartTests extends BaseTest {

    @Test(description = "Сценарий 3: добавление товара в корзину")
    @Story("Добавление товара")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Andreeva")
    public void addBackpackToCart() {
        LoginPage loginPage = new LoginPage(driver, wait);
        InventoryPage inventory = loginPage.loginAs(Credentials.STANDARD_USER, Credentials.PASSWORD);
        wait.until(ExpectedConditions.urlContains("inventory.html"));

        inventory.addBackpackToCart();
        inventory.waitForCartBadgeText("1");
        assertEquals(inventory.getCartBadgeText(), "1",
                "Счётчик корзины должен показать 1");

        CartPage cart = inventory.openCart();
        wait.until(ExpectedConditions.urlContains("cart.html"));
        assertEquals(cart.getItemName(), "Sauce Labs Backpack",
                "В корзине должен быть Sauce Labs Backpack");
        assertEquals(cart.getItemPrice(), "$29.99",
                "Цена товара в корзине должна быть $29.99");
    }

    @Test(description = "Сценарий 4: удаление товара из корзины")
    @Story("Удаление товара")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Andreeva")
    public void removeItemFromCart() {
        LoginPage loginPage = new LoginPage(driver, wait);
        InventoryPage inventory = loginPage.loginAs(Credentials.STANDARD_USER, Credentials.PASSWORD);
        wait.until(ExpectedConditions.urlContains("inventory.html"));

        inventory.addBackpackToCart();
        inventory.waitForCartBadgeText("1");

        CartPage cart = inventory.openCart();
        wait.until(ExpectedConditions.urlContains("cart.html"));

        cart.removeBackpack();
        cart.waitForCartEmpty();
        cart.waitForCartBadgeDisappears();

        assertEquals(cart.getCartItemsCount(), 0,
                "После удаления корзина должна быть пуста");
        assertFalse(cart.isCartBadgeVisible(),
                "Счётчик корзины должен исчезнуть");
    }

    @Test(description = "Сценарий 6: сортировка Price (low to high)")
    @Story("Сортировка каталога")
    @Severity(SeverityLevel.MINOR)
    @Owner("Andreeva")
    public void sortPriceLowToHigh() {
        LoginPage loginPage = new LoginPage(driver, wait);
        InventoryPage inventory = loginPage.loginAs(Credentials.STANDARD_USER, Credentials.PASSWORD);
        wait.until(ExpectedConditions.urlContains("inventory.html"));

        inventory.sortByPriceLowToHigh();
        inventory.waitForFirstItemName("Sauce Labs Onesie");

        assertEquals(inventory.getFirstItemName(), "Sauce Labs Onesie",
                "После сортировки первым должен быть самый дешёвый товар");
        assertEquals(inventory.getFirstItemPrice(), "$7.99",
                "Минимальная цена — $7.99 (Sauce Labs Onesie)");

        List<Double> prices = inventory.getItemPricesAsNumbers();
        for (int i = 0; i < prices.size() - 1; i++) {
            assertTrue(prices.get(i) <= prices.get(i + 1),
                    "Цены должны идти по возрастанию, а было: " + prices);
        }
    }
}