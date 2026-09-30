package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;
import utils.SauceDemoConfig;

/** Сценарии авторизации Swag Labs: успешный вход и негативные случаи (блокировка, ошибки валидации). */
public class LoginTests extends SauceDemoBaseTest {

    @Test(description = "Позитивно: standard_user входит и попадает в каталог Products")
    public void standardUserSeesCatalogAfterLogin() {
        InventoryPage catalog = loginAsStandardUser();

        Assert.assertTrue(driver.getCurrentUrl().contains("/inventory.html"),
                "Ожидался переход на /inventory.html, фактически URL: " + driver.getCurrentUrl());
        Assert.assertEquals(catalog.getCatalogTitle(), "Products",
                "Ожидался заголовок каталога «Products», фактически: " + catalog.getCatalogTitle());
    }

    @Test(description = "Негативно: locked_out_user не пускают, показывается сообщение о блокировке")
    public void lockedOutUserGetsBlockingMessage() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(SauceDemoConfig.LOCKED_OUT_USER, SauceDemoConfig.PASSWORD);

        String errorMessage = loginPage.getErrorMessage();
        Assert.assertEquals(errorMessage, "Epic sadface: Sorry, this user has been locked out.",
                "Ожидалось сообщение о блокировке пользователя, фактически: " + errorMessage);
        Assert.assertFalse(driver.getCurrentUrl().contains("/inventory.html"),
                "Заблокированный пользователь не должен попадать в каталог");
    }

    @DataProvider(name = "invalidCredentials")
    public Object[][] invalidCredentials() {
        return new Object[][]{
                {"", SauceDemoConfig.PASSWORD, "Epic sadface: Username is required"},
                {SauceDemoConfig.STANDARD_USER, "", "Epic sadface: Password is required"},
                {SauceDemoConfig.STANDARD_USER, "wrong_password",
                        "Epic sadface: Username and password do not match any user in this service"}
        };
    }

    @Test(dataProvider = "invalidCredentials",
            description = "Негативно: невалидные данные входа — точный текст ошибки")
    public void invalidCredentialsShowExactError(String username, String password, String expectedError) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        String errorMessage = loginPage.getErrorMessage();
        Assert.assertEquals(errorMessage, expectedError,
                "Ожидалось сообщение «" + expectedError + "», фактически: " + errorMessage);
    }
}
