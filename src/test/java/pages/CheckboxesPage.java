package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CheckboxesPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By checkbox = By.cssSelector("[type=checkbox]");

    public CheckboxesPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    private List<WebElement> checkboxes() {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(checkbox));
    }

    public int getCheckboxesCount() {
        return checkboxes().size();
    }

    public boolean isChecked(int index) {
        return checkboxes().get(index).isSelected();
    }

    public void clickCheckbox(int index) {
        WebElement box = checkboxes().get(index);
        wait.until(ExpectedConditions.elementToBeClickable(box)).click();
    }

    public void setChecked(int index, boolean targetState) {
        if (isChecked(index) != targetState) {
            clickCheckbox(index);
        }
    }

    public void waitForCheckedState(int index, boolean state) {
        wait.until(ExpectedConditions.elementSelectionStateToBe(checkboxes().get(index), state));
    }
}