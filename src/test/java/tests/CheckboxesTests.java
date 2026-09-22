package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.CheckboxesPage;
import pages.HomePage;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class CheckboxesTests extends BaseTest {

    @Test(description = "Первый чекбокс: изначально unchecked, клик делает его checked")
    public void firstCheckboxCanBeChecked() {
        HomePage home = new HomePage(driver, wait);
        CheckboxesPage page = home.openCheckboxes();

        assertFalse(page.isChecked(0),
                "Первый чекбокс изначально должен быть unchecked");

        page.setChecked(0, true);

        // Явное ожидание состояния после клика
        page.waitForCheckedState(0, true);
        assertTrue(page.isChecked(0),
                "После клика первый чекбокс должен стать checked");
    }

    @Test(description = "Второй чекбокс: изначально checked, клик снимает отметку")
    public void secondCheckboxCanBeUnchecked() {
        HomePage home = new HomePage(driver, wait);
        CheckboxesPage page = home.openCheckboxes();

        assertTrue(page.isChecked(1),
                "Второй чекбокс изначально должен быть checked");

        page.setChecked(1, false);

        page.waitForCheckedState(1, false);
        assertFalse(page.isChecked(1),
                "После клика второй чекбокс должен стать unchecked");
    }
}