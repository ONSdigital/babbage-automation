package com.github.onsdigital.babbage.test.page.base;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * This is the base PageObject class that all page objects inherit.
 */
public class PageObject {

    protected final WebDriver driver;

    /**
     * Constructor to define the driver.
     * @param driver
     */
    public PageObject(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Find an element in the page.
     * @param selector - the selector for the element to find.
     * @return - the element found.
     */
    protected WebElement find(By selector) {
        return driver.findElement(selector);
    }

    /**
     * Find an element, but wait for up to 5 seconds if it is not yet visible.
     * @param selector
     * @return
     */
    protected WebElement waitAndFind(By selector) {
        try {
            return (new WebDriverWait(driver, 5)).until(ExpectedConditions.visibilityOfElementLocated(selector));
        } catch (TimeoutException timeoutException) {
            System.out.println(driver.getPageSource());
            throw timeoutException;
        }
    }
}
