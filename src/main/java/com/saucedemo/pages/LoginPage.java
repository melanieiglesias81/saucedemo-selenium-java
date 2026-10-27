package com.saucedemo.pages;

import com.saucedemo.config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private final By usernameInput = By.id("user-name");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) { super(driver); }

    public LoginPage open() {
        driver.get(Config.get("baseUrl"));
        find(loginButton);
        return this;
    }

    public InventoryPage loginAs(String user, String pass) {
        submit(user, pass);
        return new InventoryPage(driver);
    }

    /** Use for negative scenarios where login should fail and we stay on this page. */
    public LoginPage submit(String user, String pass) {
        type(usernameInput, user);
        type(passwordInput, pass);
        click(loginButton);
        return this;
    }

    public String getErrorMessage() { return text(errorMessage); }
    public boolean isLoginButtonDisplayed() { return isDisplayed(loginButton); }
}
