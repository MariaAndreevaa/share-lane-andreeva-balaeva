package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.DropdownPage;
import pages.HomePage;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class DropdownTests extends BaseTest {

    @Test(description = "Негативный: по умолчанию выбран плейсхолдер, а не Option 1/2; все опции на месте")
    public void defaultStateIsPlaceholder() {
        HomePage home = new HomePage(driver, wait);
        DropdownPage page = home.openDropdown();

        assertEquals(page.getOptionsCount(), 3,
                "В дропдауне должно быть 3 опции: плейсхолдер, Option 1, Option 2");
        assertTrue(page.getOptionTexts().contains("Option 1"),
                "Опция 'Option 1' должна присутствовать");
        assertTrue(page.getOptionTexts().contains("Option 2"),
                "Опция 'Option 2' должна присутствовать");

        assertEquals(page.getSelectedText(), "Please select an option",
                "По умолчанию должен быть выбран плейсхолдер");
    }

    @Test(description = "Позитивный: выбрать Option 1 -> выбрана, выбрать Option 2 -> выбрана")
    public void selectOption1ThenOption2() {
        HomePage home = new HomePage(driver, wait);
        DropdownPage page = home.openDropdown();

        page.selectByText("Option 1");
        assertEquals(page.getSelectedText(), "Option 1",
                "После выбора должна быть выбрана 'Option 1'");

        page.selectByText("Option 2");
        assertEquals(page.getSelectedText(), "Option 2",
                "После выбора должна быть выбрана 'Option 2'");
    }
}