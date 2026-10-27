package com.saucedemo.tests;

import com.saucedemo.config.Config;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {

    @Test(description = "Valid user can log in and sees the product list")
    public void validLogin() {
        InventoryPage inventory = new LoginPage(driver).open()
                .loginAs(Config.get("username"), Config.get("password"));

        Assert.assertTrue(inventory.currentUrl().contains("inventory.html"));
        Assert.assertEquals(inventory.getTitle(), "Products");
    }

    @DataProvider(name = "invalidCredentials")
    public Object[][] invalidCredentials() {
        return new Object[][]{
                {"locked_out_user", "secret_sauce", "Epic sadface: Sorry, this user has been locked out."},
                {"standard_user", "wrong_password", "Epic sadface: Username and password do not match any user in this service"},
                {"invalid_user", "secret_sauce", "Epic sadface: Username and password do not match any user in this service"},
                {"", "secret_sauce", "Epic sadface: Username is required"},
                {"standard_user", "", "Epic sadface: Password is required"},
                {"", "", "Epic sadface: Username is required"}
        };
    }

    @Test(dataProvider = "invalidCredentials", description = "Invalid logins show the right error")
    public void invalidLogin(String user, String pass, String expectedError) {
        LoginPage login = new LoginPage(driver).open().submit(user, pass);

        Assert.assertEquals(login.getErrorMessage(), expectedError);
        Assert.assertTrue(login.isLoginButtonDisplayed(), "Should remain on login page");
    }

    @Test(description = "User can log out and returns to login page")
    public void logout() {
        LoginPage login = new LoginPage(driver).open()
                .loginAs(Config.get("username"), Config.get("password"))
                .logout();

        Assert.assertTrue(login.isLoginButtonDisplayed());
    }

    @Test(description = "Protected page redirects to login when not authenticated")
    public void directAccessWithoutLoginIsBlocked() {
        LoginPage login = new LoginPage(driver).open();
        driver.get(Config.get("baseUrl") + "inventory.html");

        Assert.assertEquals(login.getErrorMessage(),
                "Epic sadface: You can only access '/inventory.html' when you are logged in.");
    }
}
