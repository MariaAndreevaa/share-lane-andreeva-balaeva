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
import pages.CheckoutCompletePage;
import pages.CheckoutOverviewPage;
import pages.CheckoutPage;
import pages.InventoryPage;
import pages.LoginPage;
import utils.Credentials;

import static org.testng.Assert.assertEquals;

@Epic("SauceDemo")
@Feature("Checkout")
public class CheckoutTests extends BaseTest {

    private CartPage loginAndPutBackpackInCart() {
        LoginPage loginPage = new LoginPage(driver, wait);
        InventoryPage inventory = loginPage.loginAs(Credentials.STANDARD_USER, Credentials.PASSWORD);
        wait.until(ExpectedConditions.urlContains("inventory.html"));
        inventory.addBackpackToCart();
        inventory.waitForCartBadgeText("1");
        CartPage cart = inventory.openCart();
        wait.until(ExpectedConditions.urlContains("cart.html"));
        return cart;
    }

    @Test(description = "Сценарий 5: оформление заказа до 'Thank you for your order!'")
    @Story("Успешное оформление")
    @Severity(SeverityLevel.BLOCKER)
    @Owner("Andreeva")
    public void completeOrder() {
        CartPage cart = loginAndPutBackpackInCart();

        CheckoutPage checkout = cart.clickCheckout();
        wait.until(ExpectedConditions.urlContains("checkout-step-one.html"));
        checkout.fillForm("Maria", "Andreeva", "12345");

        CheckoutOverviewPage overview = checkout.clickContinue();
        wait.until(ExpectedConditions.urlContains("checkout-step-two.html"));
        assertEquals(overview.getItemName(), "Sauce Labs Backpack",
                "На Overview должен быть Sauce Labs Backpack");
        assertEquals(overview.getItemPrice(), "$29.99",
                "На Overview должна быть цена $29.99");

        CheckoutCompletePage complete = overview.clickFinish();
        wait.until(ExpectedConditions.urlContains("checkout-complete.html"));
        assertEquals(complete.getHeaderText(), "Thank you for your order!",
                "Должен появиться заголовок 'Thank you for your order!'");
    }

    @Test(description = "Дополнительно: пустая форма чекаута — ошибка обязательного поля")
    @Story("Валидация формы чекаута")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Andreeva")
    public void emptyCheckoutFormShowsError() {
        CartPage cart = loginAndPutBackpackInCart();

        CheckoutPage checkout = cart.clickCheckout();
        wait.until(ExpectedConditions.urlContains("checkout-step-one.html"));

        checkout.clickContinue(); // Continue с пустой формой

        assertEquals(checkout.getErrorText(), "Error: First Name is required",
                "Должно появиться сообщение об обязательном First Name");
    }
}