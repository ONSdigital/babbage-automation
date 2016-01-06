package com.github.onsdigital.babbage.test;

import com.github.onsdigital.babbage.test.base.BrowserTestBase;
import com.github.onsdigital.babbage.test.page.HomePage;
import com.github.webdriverextensions.Bot;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Tests to browse around the website to test the general site health and navigation.
 */
public class NavigationTest extends BrowserTestBase {

    @Test
    public void navigate() {
        HomePage home = new HomePage();
        home.open();

        WebDriver driver = Bot.driver();
        WebDriverWait wait = new WebDriverWait(driver, 5); // Pause for 5 seconds
        Actions actions = new Actions(driver);

        //Find and click economy navigation link
        WebElement economy = driver.findElement(By.linkText("Economy"));
        try {
            economy.click();
            Bot.assertCurrentUrlEndsWith("/economy"); // Check url ends in economy
        } catch (NoSuchElementException e) {
            System.out.println("No economy link available to click");
        }

        //Hover on tile and click
        WebElement clickableWrap = waitAndFind(By.className("clickable-wrap")); //Add wait to WebElement so it is enhanced
        actions.moveToElement(clickableWrap).build().perform(); //Delayed hover on clickable wrap, to make sure js enhanced TODO - make this action reusable ( ie 'hoverOn(clickableWrap)' )
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("tiles__item--hover")));//Keep hovering until tiles__item--hover is present
        try {
            WebElement tileHover = driver.findElement(By.className("tiles__item--hover"));
            tileHover.click();
        } catch (NoSuchElementException e) {
            System.out.println("No enhanced hover");
        }

        //Hover on navigation link and click child
        WebElement navLink = driver.findElement(By.className("js-expandable"));
        actions.moveToElement(navLink).build().perform();// Hover over nav link
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("primary-nav__child-link"))); // Wait until child nav link is displayed
        try {
            WebElement navChild = driver.findElement(By.className("primary-nav__child-link"));
            navChild.click();
        } catch (NoSuchElementException e) {
            System.out.println("No child link in navigation");
        }

        //Click second breadcrumb link
        WebElement breadcrumbLink = driver.findElement(By.xpath("(//a[contains(@class, 'breadcrumb__link')])[2]"));
        breadcrumbLink.click();
        System.out.println("Successfully navigated to " + driver.getCurrentUrl());

    }
}
