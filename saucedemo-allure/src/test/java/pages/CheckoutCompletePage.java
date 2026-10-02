package pages;

import base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutCompletePage extends BasePage {

    private final By completeHeader = By.cssSelector("h2.complete-header");

    public CheckoutCompletePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Step("Прочитать итоговый заголовок")
    public String getHeaderText() {
        return el(completeHeader).getText();
    }
}