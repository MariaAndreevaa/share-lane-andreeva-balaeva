package tests;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;
import utils.Credentials;

import static org.testng.Assert.assertEquals;

@Epic("SauceDemo")
@Feature("Login")
public class LoginTests extends BaseTest {

    @Test(description = "Сценарий 1: успешный логин standard_user")
    @Story("Успешная авторизация")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Andreeva")
    public void positiveLoginAsStandardUser() {
        LoginPage loginPage = new LoginPage(driver, wait);
        InventoryPage inventory = loginPage.loginAs(Credentials.STANDARD_USER, Credentials.PASSWORD);

        wait.until(ExpectedConditions.urlContains("inventory.html"));
        assertEquals(inventory.getTitleText(), "Products",
                "После логина должен открыться каталог с заголовком 'Products'");
    }

    @Test(description = "Сценарий 2: негативный логин locked_out_user")
    @Story("Блокированный пользователь")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Andreeva")
    public void lockedOutUserSeesError() {
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.loginAs(Credentials.LOCKED_OUT_USER, Credentials.PASSWORD);

        assertEquals(loginPage.getErrorText(),
                "Epic sadface: Sorry, this user has been locked out.",
                "Должно появиться сообщение о заблокированном пользователе");
    }

    @Test(description = "Негативный: пустое имя пользователя")
    @Story("Валидация формы логина")
    @Severity(SeverityLevel.MINOR)
    @Owner("Andreeva")
    public void emptyUsernameShowsError() {
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.clickLogin();

        assertEquals(loginPage.getErrorText(),
                "Epic sadface: Username is required",
                "Должно появиться сообщение 'Username is required'");
    }
}