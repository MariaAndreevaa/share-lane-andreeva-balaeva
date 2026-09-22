package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddRemoveElementsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By addButton = By.xpath("//button[text()='Add Element']");
    private final By deleteButton = By.xpath("//button[text()='Delete']");

    public AddRemoveElementsPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void addElement() {
        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
    }

    public void addElements(int count) {
        for (int i = 0; i < count; i++) {
            addElement();
        }
    }

    public void deleteElement() {
        wait.until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
    }

    public int getDeleteButtonsCount() {
        return driver.findElements(deleteButton).size();
    }

    public void waitForDeleteButtonsCount(int expected) {
        wait.until(ExpectedConditions.numberOfElementsToBe(deleteButton, expected));
    }
}