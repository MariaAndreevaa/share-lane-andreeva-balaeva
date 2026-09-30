package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.SortOption;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/** Сценарии сортировки каталога Swag Labs: по названию и по цене. */
public class SortingTests extends SauceDemoBaseTest {

    @Test(description = "Позитивно: сортировка Name (A to Z) упорядочивает товары по алфавиту")
    public void nameAscendingSortsAlphabetically() {
        InventoryPage catalog = loginAsStandardUser();

        List<String> expectedNames = new ArrayList<>(catalog.getItemNames());
        Collections.sort(expectedNames);
        catalog.sortBy(SortOption.NAME_ASC);

        Assert.assertEquals(catalog.getItemNames(), expectedNames,
                "После сортировки A→Z товары должны быть упорядочены по алфавиту");
    }

    @Test(description = "Позитивно: сортировка Name (Z to A) упорядочивает товары по алфавиту в обратном порядке")
    public void nameDescendingSortsReverseAlphabetically() {
        InventoryPage catalog = loginAsStandardUser();

        List<String> expectedNames = new ArrayList<>(catalog.getItemNames());
        expectedNames.sort(Comparator.reverseOrder());
        catalog.sortBy(SortOption.NAME_DESC);

        Assert.assertEquals(catalog.getItemNames(), expectedNames,
                "После сортировки Z→A товары должны идти по алфавиту в обратном порядке");
    }

    @Test(description = "Позитивно: сортировка Price (low to high) упорядочивает цены по возрастанию")
    public void priceLowToHighSortsAscending() {
        InventoryPage catalog = loginAsStandardUser();

        List<Double> expectedPrices = new ArrayList<>(catalog.getItemPrices());
        Collections.sort(expectedPrices);
        catalog.sortBy(SortOption.PRICE_LOW_TO_HIGH);

        Assert.assertEquals(catalog.getItemPrices(), expectedPrices,
                "После сортировки Low→High цены должны идти по возрастанию");
    }

    @Test(description = "Позитивно: сортировка Price (high to low) упорядочивает цены по убыванию")
    public void priceHighToLowSortsDescending() {
        InventoryPage catalog = loginAsStandardUser();

        List<Double> expectedPrices = new ArrayList<>(catalog.getItemPrices());
        expectedPrices.sort(Comparator.reverseOrder());
        catalog.sortBy(SortOption.PRICE_HIGH_TO_LOW);

        Assert.assertEquals(catalog.getItemPrices(), expectedPrices,
                "После сортировки High→Low цены должны идти по убыванию");
    }
}
