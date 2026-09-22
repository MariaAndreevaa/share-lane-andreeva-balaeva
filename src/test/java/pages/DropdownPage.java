package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class DropdownPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By dropdown = By.id("dropdown");

    public DropdownPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    private Select select() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(dropdown));
        return new Select(element);
    }

    public int getOptionsCount() {
        return select().getOptions().size();
    }

    public List<String> getOptionTexts() {
        List<String> texts = new ArrayList<>();
        for (WebElement option : select().getOptions()) {
            texts.add(option.getText());
        }
        return texts;
    }

    public void selectByText(String text) {
        select().selectByVisibleText(text);
    }

    public String getSelectedText() {
        return select().getFirstSelectedOption().getText();
    }
}