package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.InventoryPage;

import java.util.List;

/** Сценарии работы с корзиной Swag Labs: добавление, удаление, состав корзины. */
public class CartTests extends SauceDemoBaseTest {

    private static final String BACKPACK_NAME = "Sauce Labs Backpack";
    private static final double BACKPACK_PRICE = 29.99;
    private static final String BIKE_LIGHT_NAME = "Sauce Labs Bike Light";
    private static final double BIKE_LIGHT_PRICE = 9.99;

    @Test(description = "Позитивно: добавленный товар попадает в корзину с верной ценой")
    public void addedItemAppearsInCartWithPrice() {
        InventoryPage catalog = loginAsStandardUser();

        catalog.addItemToCart(InventoryPage.ITEM_BACKPACK);
        Assert.assertEquals(catalog.getCartBadgeCount(), 1,
                "После добавления одного товара счётчик корзины должен равняться 1");

        CartPage cart = catalog.openCart();
        Assert.assertEquals(cart.getItemNames(), List.of(BACKPACK_NAME),
                "Ожидался товар «Sauce Labs Backpack» в корзине");
        Assert.assertEquals(cart.getItemPrices(), List.of(BACKPACK_PRICE),
                "Ожидалась цена 29.99 у товара в корзине");
    }

    @Test(description = "Позитивно: два товара добавляются, один удаляется — в корзине остаётся второй")
    public void removeItemLeavesRestInCart() {
        InventoryPage catalog = loginAsStandardUser();

        catalog.addItemToCart(InventoryPage.ITEM_BACKPACK);
        catalog.addItemToCart(InventoryPage.ITEM_BIKE_LIGHT);
        Assert.assertEquals(catalog.getCartBadgeCount(), 2,
                "После добавления двух товаров счётчик корзины должен равняться 2");

        catalog.removeItemFromCart(InventoryPage.ITEM_BACKPACK);
        Assert.assertEquals(catalog.getCartBadgeCount(), 1,
                "После удаления одного товара счётчик корзины должен равняться 1");

        CartPage cart = catalog.openCart();
        Assert.assertEquals(cart.getItemNames(), List.of(BIKE_LIGHT_NAME),
                "После удаления рюкзака в корзине должен остаться «Sauce Labs Bike Light»");
        Assert.assertEquals(cart.getItemPrices(), List.of(BIKE_LIGHT_PRICE),
                "Ожидалась цена 9.99 у оставшегося товара");
    }
}
