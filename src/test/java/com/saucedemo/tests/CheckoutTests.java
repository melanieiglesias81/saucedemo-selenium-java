package com.saucedemo.tests;

import com.saucedemo.config.Config;
import com.saucedemo.pages.CheckoutPage;
import com.saucedemo.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CheckoutTests extends BaseTest {
    private CheckoutPage checkout;

    @BeforeMethod
    public void goToCheckout() {
        checkout = new LoginPage(driver).open()
                .loginAs(Config.get("username"), Config.get("password"))
                .addToCart("Sauce Labs Backpack")
                .addToCart("Sauce Labs Bike Light")
                .goToCart()
                .checkout();
    }

    @Test(description = "End-to-end purchase completes successfully")
    public void completePurchase() {
        checkout.fillInformation("John", "Doe", "4102").finish();
        Assert.assertEquals(checkout.getConfirmationMessage(), "Thank you for your order!");
    }

    @Test(description = "Order summary shows the correct item total")
    public void summaryShowsCorrectSubtotal() {
        checkout.fillInformation("John", "Doe", "4102");
        // Backpack $29.99 + Bike Light $9.99
        Assert.assertEquals(checkout.getSubtotalText(), "Item total: $39.98");
    }

    @Test(description = "First name is required")
    public void firstNameRequired() {
        checkout.clickContinue();
        Assert.assertEquals(checkout.getErrorMessage(), "Error: First Name is required");
    }

    @Test(description = "Last name is required")
    public void lastNameRequired() {
        checkout.fillInformation("John", "", "4102");
        Assert.assertEquals(checkout.getErrorMessage(), "Error: Last Name is required");
    }

    @Test(description = "Postal code is required")
    public void postalCodeRequired() {
        checkout.fillInformation("John", "Doe", "");
        Assert.assertEquals(checkout.getErrorMessage(), "Error: Postal Code is required");
    }
}
