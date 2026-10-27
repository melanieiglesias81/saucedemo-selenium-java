package com.saucedemo.tests;

import com.saucedemo.config.Config;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InventoryTests extends BaseTest {
    private InventoryPage inventory;

    @BeforeMethod
    public void login() {
        inventory = new LoginPage(driver).open()
                .loginAs(Config.get("username"), Config.get("password"));
    }

    @Test(description = "Inventory shows all 6 products")
    public void showsSixProducts() {
        Assert.assertEquals(inventory.getProductCount(), 6);
    }

    @Test(description = "Sort by name A to Z")
    public void sortNameAscending() {
        List<String> actual = inventory.sortBy("az").getProductNames();
        List<String> expected = new ArrayList<>(actual);
        Collections.sort(expected);
        Assert.assertEquals(actual, expected);
    }

    @Test(description = "Sort by name Z to A")
    public void sortNameDescending() {
        List<String> actual = inventory.sortBy("za").getProductNames();
        List<String> expected = new ArrayList<>(actual);
        expected.sort(Collections.reverseOrder());
        Assert.assertEquals(actual, expected);
    }

    @Test(description = "Sort by price low to high")
    public void sortPriceAscending() {
        List<Double> actual = inventory.sortBy("lohi").getProductPrices();
        List<Double> expected = new ArrayList<>(actual);
        Collections.sort(expected);
        Assert.assertEquals(actual, expected);
    }

    @Test(description = "Sort by price high to low")
    public void sortPriceDescending() {
        List<Double> actual = inventory.sortBy("hilo").getProductPrices();
        List<Double> expected = new ArrayList<>(actual);
        expected.sort(Collections.reverseOrder());
        Assert.assertEquals(actual, expected);
    }
}
