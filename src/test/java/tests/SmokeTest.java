package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SmokeTest extends BaseTest {

    @Test
    public void homePageOpens() {
        assertTrue(wait.until(ExpectedConditions.titleIs("The Internet")),
                "Заголовок страницы должен быть 'The Internet'");
    }

    @Test
    public void homePageShowsWelcomeHeading() {
        WebElement heading = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
        assertEquals(heading.getText(), "Welcome to the-internet",
                "На главной должен быть заголовок 'Welcome to the Internet'");
    }
}