package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final String BASE_URL = "https://the-internet.herokuapp.com/";

    private final By addRemoveElementsLink = By.linkText("Add/Remove Elements");
    private final By checkboxesLink = By.linkText("Checkboxes");
    private final By dropdownLink = By.linkText("Dropdown");
    private final By inputsLink = By.linkText("Inputs");

    public HomePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void open() {
        driver.get(BASE_URL);
    }

    private void clickLink(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public AddRemoveElementsPage openAddRemoveElements() {
        clickLink(addRemoveElementsLink);
        return new AddRemoveElementsPage(driver, wait);
    }

    public CheckboxesPage openCheckboxes() {
        clickLink(checkboxesLink);
        return new CheckboxesPage(driver, wait);
    }

    public DropdownPage openDropdown() {
        clickLink(dropdownLink);
        return new DropdownPage(driver, wait);
    }

    public InputsPage openInputs() {
        clickLink(inputsLink);
        return new InputsPage(driver, wait);
    }
}