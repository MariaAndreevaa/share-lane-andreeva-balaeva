package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class InputsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By numberInput = By.tagName("input");

    public InputsPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void typeText(String text) {
        WebElement field = wait.until(ExpectedConditions.elementToBeClickable(numberInput));
        field.clear();
        field.sendKeys(text);
    }

    public void pressArrowUp() {
        WebElement field = wait.until(ExpectedConditions.elementToBeClickable(numberInput));
        field.sendKeys(Keys.ARROW_UP);
    }

    public void pressArrowDown() {
        WebElement field = wait.until(ExpectedConditions.elementToBeClickable(numberInput));
        field.sendKeys(Keys.ARROW_DOWN);
    }

    public String getValue() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(numberInput))
                .getAttribute("value");
    }
}