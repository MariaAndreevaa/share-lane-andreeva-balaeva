package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.InputsPage;

import static org.testng.Assert.assertEquals;

public class InputsTests extends BaseTest {

    @Test(description = "Позитивный: ввести 5, нажать ARROW_UP — значение стало 6")
    public void arrowUpIncrementsValue() {
        HomePage home = new HomePage(driver, wait);
        InputsPage page = home.openInputs();

        page.typeText("5");
        assertEquals(page.getValue(), "5", "В поле должно быть '5' после ввода");

        page.pressArrowUp();
        assertEquals(page.getValue(), "6",
                "После ARROW_UP значение должно увеличиться на 1");
    }

    @Test(description = "Позитивный: ввести 10, нажать ARROW_DOWN — значение стало 9")
    public void arrowDownDecrementsValue() {
        HomePage home = new HomePage(driver, wait);
        InputsPage page = home.openInputs();

        page.typeText("10");
        page.pressArrowDown();
        assertEquals(page.getValue(), "9",
                "После ARROW_DOWN значение должно уменьшиться на 1");
    }

    @Test(description = "Негативный: буквы в числовое поле не вводятся — значение остаётся пустым")
    public void nonNumericValueIgnored() {
        HomePage home = new HomePage(driver, wait);
        InputsPage page = home.openInputs();

        page.typeText("abc");

        assertEquals(page.getValue(), "",
                "Нечисловое значение не должно попасть в поле");
    }
}