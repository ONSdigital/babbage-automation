package com.github.onsdigital.babbage.test;

import com.github.onsdigital.babbage.test.base.BrowserTestBase;
import com.github.onsdigital.babbage.test.page.Navigation;
import com.github.webdriverextensions.Bot;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Tests to browse around the website to test the general site health and navigation.
 */
public class NavigationTest extends BrowserTestBase {

    Navigation page = new Navigation("/");

    @Test
    public void navigate() {
        WebDriver driver = Bot.driver();
        page.open();

        //Find and click economy navigation link
        WebElement economy = driver.findElement(By.linkText("Economy"));
        try {
            economy.click();
            Bot.assertCurrentUrlEndsWith("/economy"); // Check url ends in economy
        } catch (NoSuchElementException e) {
            System.out.println("No economy link available to click");
        }
    }

    @Test
    public void navigationHover() {
        WebDriver driver = Bot.driver();
        WebDriverWait wait = new WebDriverWait(driver, 5); // Pause for 5 seconds
        Actions actions = new Actions(driver);
        page.open();

        //Hover on navigation link and confirm child is displaying and background colour changed
        WebElement navItem = driver.findElement(By.xpath("//li[ul[contains(@class, 'primary-nav__child-list')]]"));
        WebElement navLink = navItem.findElement(By.className("primary-nav__link"));
        String navLinkBg = navLink.getCssValue("background-color");
        actions.moveToElement(navItem).build().perform();// Hover over nav item
        String navLinkHoverBg = navLink.getCssValue("background-color"); // Get background colour on hover
        Assert.assertNotEquals(navLinkBg, navLinkHoverBg);// Check colour has changed from original to hover state
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("primary-nav__child-link"))); // Wait until child nav link is displayed

        //Hover on child and confirm background colour change
        WebElement navChild = driver.findElement(By.className("primary-nav__child-link")); // Same as previous hover colour check
        String navChildBg = navChild.getCssValue("background-color");
        actions.moveToElement(navChild).build().perform();
        String navChildHoverBg = navChild.getCssValue("background-color");
        Assert.assertNotEquals(navChildBg, navChildHoverBg);

        //Attempt to click child link
        try {
            navChild.click();
        } catch (NoSuchElementException e) {
            System.out.println("No child link in navigation");
        }
    }

    @Test
    public void navigationActive() {
        Navigation page = new Navigation("/economy/grossdomesticproductgdp");
        page.open();
        WebDriver driver = Bot.driver();

        // Confirm active links in nav are displaying active color
        String activeColour = "rgba(15, 130, 67, 1)";
        String parentLinkColour = driver.findElement(By.xpath("//li[contains(@class, 'primary-nav__item')][.//a[contains(@href, 'economy')]]")).getCssValue("background-color");
        String childLinkColor = driver.findElement(By.xpath("//li[contains(@class, 'primary-nav__child-item')][.//a[contains(@href, '/economy/grossdomesticproductgdp')]]")).getCssValue("background-color");
        Assert.assertEquals(parentLinkColour, activeColour);
        Assert.assertEquals(childLinkColor, activeColour);
    }

//    @Test
//    public void breadcrumb() {
//        //Click second breadcrumb link
//        WebElement breadcrumbLink = driver.findElement(By.xpath("(//a[contains(@class, 'breadcrumb__link')])[2]"));
//        breadcrumbLink.click();
//        System.out.println("Successfully navigated to " + driver.getCurrentUrl());
//    }
}
