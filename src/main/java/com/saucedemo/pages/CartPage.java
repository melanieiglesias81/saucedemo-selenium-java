package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class CartPage extends BasePage {
    private final By cartItemNames = By.cssSelector("[data-test='inventory-item-name']");
    private final By checkoutButton = By.cssSelector("[data-test='checkout']");
    private final By continueShopping = By.cssSelector("[data-test='continue-shopping']");

    public CartPage(WebDriver driver) { super(driver); }

    public List<String> getItemNames() {
        find(checkoutButton); // ensure page loaded
        return findAll(cartItemNames).stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public CartPage removeItem(String productName) {
        click(By.cssSelector("[data-test='remove-" + slug(productName) + "']"));
        return this;
    }

    public InventoryPage continueShopping() {
        click(continueShopping);
        return new InventoryPage(driver);
    }

    public CheckoutPage checkout() {
        click(checkoutButton);
        return new CheckoutPage(driver);
    }
}
