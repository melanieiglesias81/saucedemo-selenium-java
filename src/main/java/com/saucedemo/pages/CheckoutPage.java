package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {
    private final By firstName = By.cssSelector("[data-test='firstName']");
    private final By lastName = By.cssSelector("[data-test='lastName']");
    private final By postalCode = By.cssSelector("[data-test='postalCode']");
    private final By continueButton = By.cssSelector("[data-test='continue']");
    private final By finishButton = By.cssSelector("[data-test='finish']");
    private final By error = By.cssSelector("[data-test='error']");
    private final By subtotal = By.cssSelector("[data-test='subtotal-label']");
    private final By total = By.cssSelector("[data-test='total-label']");
    private final By completeHeader = By.cssSelector("[data-test='complete-header']");

    public CheckoutPage(WebDriver driver) { super(driver); }

    public CheckoutPage fillInformation(String first, String last, String zip) {
        type(firstName, first);
        type(lastName, last);
        type(postalCode, zip);
        click(continueButton);
        return this;
    }

    /** Submits the form without filling anything in, for validation tests. */
    public CheckoutPage clickContinue() {
        click(continueButton);
        return this;
    }

    public CheckoutPage finish() {
        click(finishButton);
        return this;
    }

    public String getErrorMessage() { return text(error); }
    public String getSubtotalText() { return text(subtotal); }
    public String getTotalText() { return text(total); }
    public String getConfirmationMessage() { return text(completeHeader); }
}
