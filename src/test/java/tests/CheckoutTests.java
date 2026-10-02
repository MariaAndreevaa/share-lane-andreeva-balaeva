package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CheckoutCompletePage;
import pages.CheckoutOverviewPage;
import pages.CheckoutPage;
import pages.InventoryPage;

import java.util.List;

/** Сценарии оформления заказа Swag Labs: полный цикл и валидация обязательных полей. */
public class CheckoutTests extends SauceDemoBaseTest {

    private static final String FIRST_NAME = "Ivan";
    private static final String LAST_NAME = "Petrov";
    private static final String POSTAL_CODE = "101000";

    private static final String BACKPACK_NAME = "Sauce Labs Backpack";
    private static final double BACKPACK_PRICE = 29.99;

    @Test(description = "Позитивно: полный цикл заказа — данные покупателя, сверка сумм, подтверждение")
    public void fullCheckoutCycleEndsWithConfirmation() {
        InventoryPage catalog = loginAsStandardUser();
        catalog.addItemToCart(InventoryPage.ITEM_BACKPACK);

        CheckoutPage form = catalog.openCart().startCheckout();
        form.fillCustomerInfo(FIRST_NAME, LAST_NAME, POSTAL_CODE);
        CheckoutOverviewPage overview = form.continueToOverview();

        Assert.assertEquals(overview.getItemNames(), List.of(BACKPACK_NAME),
                "На шаге Overview ожидался товар «Sauce Labs Backpack»");
        Assert.assertEquals(overview.getSubtotalAmount(), BACKPACK_PRICE,
                "Ожидалась сумма товаров 29.99 на шаге Overview");
        Assert.assertEquals(overview.getTotalAmount(), overview.getSubtotalAmount() + overview.getTaxAmount(),
                0.001, "Итоговая сумма должна равняться сумме товаров плюс налог");

        CheckoutCompletePage completePage = overview.finishOrder();
        Assert.assertEquals(completePage.getCompleteHeaderText(), "Thank you for your order!",
                "Ожидалось подтверждение оформления заказа");
    }

    @Test(description = "Негативно: пустое обязательное поле First Name блокирует переход на Overview")
    public void emptyFirstNameBlocksCheckout() {
        InventoryPage catalog = loginAsStandardUser();
        catalog.addItemToCart(InventoryPage.ITEM_BACKPACK);

        CheckoutPage form = catalog.openCart().startCheckout();
        form.fillCustomerInfo("", LAST_NAME, POSTAL_CODE);
        form.clickContinueExpectingError();

        Assert.assertEquals(form.getErrorMessage(), "Error: First Name is required",
                "Ожидалась ошибка обязательного поля First Name");
    }
}
