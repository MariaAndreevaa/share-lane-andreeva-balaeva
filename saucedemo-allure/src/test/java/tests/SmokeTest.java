package tests;

import base.BaseTest;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

@Epic("SauceDemo")
@Feature("Smoke")
public class SmokeTest extends BaseTest {

    @Test(description = "Сайт открывается, заголовок страницы логина — 'Swag Labs'")
    @Story("Открытие сайта")
    @Severity(SeverityLevel.BLOCKER)
    @Owner("Andreeva")
    public void loginPageOpens() {
        // Allure.step — вариант без аннотации: шаг появится в отчёте
        Allure.step("Проверяем заголовок страницы логина", () ->
                assertTrue(wait.until(ExpectedConditions.titleIs("Swag Labs")),
                        "Заголовок страницы логина должен быть 'Swag Labs'"));
    }
}