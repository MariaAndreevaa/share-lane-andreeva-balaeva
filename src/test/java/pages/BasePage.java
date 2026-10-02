package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.SauceDemoConfig;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Базовый класс Page Object: хранит драйвер и явное ожидание с единым таймаутом
 * из {@link SauceDemoConfig}, предоставляет обёртки над типовыми действиями.
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, SauceDemoConfig.EXPLICIT_TIMEOUT);
    }

    protected WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void waitForUrlContains(String urlFragment) {
        wait.until(ExpectedConditions.urlContains(urlFragment));
    }

    protected List<WebElement> waitForAllVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    protected void click(By locator) {
        waitForClickable(locator).click();
    }

    protected void type(By locator, String text) {
        WebElement field = waitForVisible(locator);
        field.clear();
        field.sendKeys(text);
    }

    protected String textOf(By locator) {
        return waitForVisible(locator).getText();
    }

    protected List<String> textsOfAll(By locator) {
        return waitForAllVisible(locator).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Кликает и дожидается наблюдаемого эффекта. Страницы saucedemo построены на React:
     * клик, пришедший до перепривязки обработчиков к обновлённому DOM, может быть
     * «проглочен», поэтому клик выполняется повторно, пока эффект не наступит
     * в пределах таймаута явных ожиданий.
     *
     * @param effect условие, подтверждающее срабатывание клика (изменение URL, счётчика и т.п.)
     */
    protected void clickAndWaitForEffect(By locator, Predicate<WebDriver> effect) {
        for (int attempt = 1; attempt <= 3; attempt++) {
            waitForClickable(locator).click();
            try {
                wait.until(d -> effect.test(d));
                return;
            } catch (TimeoutException e) {
                if (attempt == 3) {
                    throw new TimeoutException(
                            "Клик по " + locator + " не привёл к ожидаемому эффекту после " + attempt + " попыток", e);
                }
            }
        }
    }

    public String currentUrl() {
        return driver.getCurrentUrl();
    }
}
