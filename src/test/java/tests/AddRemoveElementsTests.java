package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.AddRemoveElementsPage;
import pages.HomePage;

import static org.testng.Assert.assertEquals;

public class AddRemoveElementsTests extends BaseTest {

    @Test(description = "Добавить 2 элемента, удалить 1 — осталась 1 кнопка Delete")
    public void addTwoElementsAndDeleteOne() {
        HomePage home = new HomePage(driver, wait);
        AddRemoveElementsPage page = home.openAddRemoveElements();

        page.addElements(2);

        page.waitForDeleteButtonsCount(2);
        assertEquals(page.getDeleteButtonsCount(), 2,
                "После двух добавлений должно быть 2 кнопки Delete");

        page.deleteElement();

        page.waitForDeleteButtonsCount(1);
        assertEquals(page.getDeleteButtonsCount(), 1,
                "После удаления одного элемента должна остаться 1 кнопка Delete");
    }

    @Test(description = "Негативный/граничный: удалить все элементы — кнопок Delete не осталось")
    public void deleteAllElements() {
        HomePage home = new HomePage(driver, wait);
        AddRemoveElementsPage page = home.openAddRemoveElements();

        page.addElements(3);
        page.waitForDeleteButtonsCount(3);

        // Удаляем все три элемента подряд
        page.deleteElement();
        page.deleteElement();
        page.deleteElement();

        page.waitForDeleteButtonsCount(0);
        assertEquals(page.getDeleteButtonsCount(), 0,
                "После удаления всех элементов кнопок Delete быть не должно");
    }
}