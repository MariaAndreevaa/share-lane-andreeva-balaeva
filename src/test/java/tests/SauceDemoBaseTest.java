package tests;

import base.BaseTest;
import pages.InventoryPage;
import pages.LoginPage;
import utils.SauceDemoConfig;

/** Общий слой saucedemo-сценариев: типовой вход под standard_user. */
public abstract class SauceDemoBaseTest extends BaseTest {

    /** Открывает страницу входа и авторизуется под standard_user; возвращает каталог. */
    protected InventoryPage loginAsStandardUser() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(SauceDemoConfig.STANDARD_USER, SauceDemoConfig.PASSWORD);
        return new InventoryPage(driver);
    }
}
