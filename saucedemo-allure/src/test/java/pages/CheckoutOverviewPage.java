package pages;

import base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutOverviewPage extends BasePage {

    private final By itemName = By.cssSelector(".inventory_item_name");
    private final By itemPrice = By.cssSelector(".inventory_item_price");
    private final By finishButton = By.id("finish");

    public CheckoutOverviewPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Step("Прочитать название товара в сводке заказа")
    public String getItemName() {
        return el(itemName).getText();
    }

    @Step("Прочитать цену товара в сводке заказа")
    public String getItemPrice() {
        return el(itemPrice).getText();
    }

    @Step("Нажать Finish")
    public CheckoutCompletePage clickFinish() {
        click(finishButton);
        return new CheckoutCompletePage(driver, wait);
    }
}