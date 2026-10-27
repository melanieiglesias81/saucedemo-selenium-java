package com.saucedemo.tests;

import com.saucedemo.config.Config;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class CartTests extends BaseTest {
    private static final String BACKPACK = "Sauce Labs Backpack";
    private static final String BIKE_LIGHT = "Sauce Labs Bike Light";

    private InventoryPage inventory;

    @BeforeMethod
    public void login() {
        inventory = new LoginPage(driver).open()
                .loginAs(Config.get("username"), Config.get("password"));
    }

    @Test(description = "Cart badge reflects number of added items")
    public void badgeUpdatesWhenAddingItems() {
        Assert.assertEquals(inventory.getCartCount(), 0);
        inventory.addToCart(BACKPACK);
        Assert.assertEquals(inventory.getCartCount(), 1);
        inventory.addToCart(BIKE_LIGHT);
        Assert.assertEquals(inventory.getCartCount(), 2);
    }

    @Test(description = "Removing from inventory page decrements badge")
    public void removeFromInventoryPage() {
        inventory.addToCart(BACKPACK).removeFromCart(BACKPACK);
        Assert.assertEquals(inventory.getCartCount(), 0);
    }

    @Test(description = "Cart page lists the items that were added")
    public void cartContainsAddedItems() {
        CartPage cart = inventory.addToCart(BACKPACK).addToCart(BIKE_LIGHT).goToCart();
        List<String> items = cart.getItemNames();

        Assert.assertEquals(items.size(), 2);
        Assert.assertTrue(items.contains(BACKPACK));
        Assert.assertTrue(items.contains(BIKE_LIGHT));
    }

    @Test(description = "Item can be removed from the cart page")
    public void removeItemFromCartPage() {
        CartPage cart = inventory.addToCart(BACKPACK).addToCart(BIKE_LIGHT).goToCart();
        cart.removeItem(BACKPACK);

        List<String> items = cart.getItemNames();
        Assert.assertEquals(items.size(), 1);
        Assert.assertFalse(items.contains(BACKPACK));
    }
}
