package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

public class InventoryPage extends BasePage {
    private final By title = By.cssSelector("[data-test='title']");
    private final By itemNames = By.cssSelector("[data-test='inventory-item-name']");
    private final By itemPrices = By.cssSelector("[data-test='inventory-item-price']");
    private final By sortDropdown = By.cssSelector("[data-test='product-sort-container']");
    private final By cartBadge = By.cssSelector("[data-test='shopping-cart-badge']");
    private final By cartLink = By.cssSelector("[data-test='shopping-cart-link']");
    private final By menuButton = By.id("react-burger-menu-btn");
    private final By logoutLink = By.id("logout_sidebar_link");

    public InventoryPage(WebDriver driver) { super(driver); }

    public String getTitle() { return text(title); }

    public int getProductCount() { return findAll(itemNames).size(); }

    public List<String> getProductNames() {
        return findAll(itemNames).stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public List<Double> getProductPrices() {
        return findAll(itemPrices).stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "")))
                .collect(Collectors.toList());
    }

    /** @param value one of: az, za, lohi, hilo */
    public InventoryPage sortBy(String value) {
        new Select(find(sortDropdown)).selectByValue(value);
        return this;
    }

    public InventoryPage addToCart(String productName) {
        click(By.cssSelector("[data-test='add-to-cart-" + slug(productName) + "']"));
        return this;
    }

    public InventoryPage removeFromCart(String productName) {
        click(By.cssSelector("[data-test='remove-" + slug(productName) + "']"));
        return this;
    }

    public int getCartCount() {
        return isDisplayed(cartBadge) ? Integer.parseInt(text(cartBadge)) : 0;
    }

    public CartPage goToCart() {
        click(cartLink);
        return new CartPage(driver);
    }

    public LoginPage logout() {
        click(menuButton);
        click(logoutLink);
        return new LoginPage(driver);
    }
}
