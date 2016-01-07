package com.github.onsdigital.babbage.test;

import com.github.onsdigital.babbage.test.base.BrowserTestBase;
import com.github.onsdigital.babbage.test.page.Tiles;
import com.github.webdriverextensions.Bot;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by crispin on 06/01/2016.
 *
 * Test tile js enhancements:
 * - hovering over tile changes tile background colour
 * - clicking on the tile navigates to url of first available link in the tile
 *
 */
public class TilesTest extends BrowserTestBase {

    public void tileHover(WebElement tileElement, String hoverClass) {
        WebDriver driver = Bot.driver();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        Actions actions = new Actions(driver);

        // Hover on element with enhanced class on it
        String tileBg = tileElement.getCssValue("background-color"); // Store original tile colour, pre-hover
        actions.moveToElement(tileElement).build().perform(); //Delayed hover on tile, to make sure js enhanced TODO - make this action reusable ( ie 'hoverOn(clickableWrap)' );
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(hoverClass)));
        String tileBgHover = tileElement.getCssValue("background-color"); // Store tile colour on hover
        Assert.assertNotEquals(tileBg, tileBgHover); // Check pre and post-hover colours are different

        // Click the tile to check clickable-wrap enhancement
        WebElement tileLink = tileElement.findElement(By.tagName("a"));
        String tileHref = tileLink.getAttribute("href");
        tileElement.click();
        Bot.assertCurrentUrlEquals(tileHref);
    }

    @Test
    public void landingPageTileHover() {
        // Check landing page tile hover state and click
        Tiles tile = new Tiles("/economy");
        tile.open();
        WebElement tileElement = Bot.driver().findElement(By.className("tiles__item")); // Get tile item inside wrap
        tileHover(tileElement, "tiles__item--hover");
    }

    @Test
    public void homeTileHover() {
        // Check home page tile hover state and click
        Tiles tile = new Tiles("");
        tile.open();
        WebElement tileElement = Bot.driver().findElement(By.className("js-hover"));
        tileHover(tileElement, "js-hover");
    }

    @Test
    public void productPageTileHover() {
        // Check headline hover state and click
        Tiles headlineTile = new Tiles("/economy/environmentalaccounts");
        headlineTile.open();
        WebElement headlineTileElement = Bot.driver().findElement(By.className("tiles__item--image-type-headline"));
        tileHover(headlineTileElement, "tiles__item--hover");

        // Check time series data hover state and click
        Tiles dataTiles = new Tiles("/businessindustryandtrade/tourismindustry");
        dataTiles.open();
        WebElement timeSeriesTileElement = Bot.driver().findElement(By.className("tiles__item--list-type"));
        tileHover(timeSeriesTileElement, "tiles__item--hover");

        // Check spreadsheets hover state and click
        dataTiles.open();
        WebElement spreadsheetsTileElement = Bot.driver().findElement(By.className("tiles__item--list-type-mercury"));
        tileHover(spreadsheetsTileElement, "tiles__item--hover");
    }

    @Test
    public void searchResultsTileHover() {
        // Check call action for time series explorer tool hover states
        Tiles dataExplorerTile = new Tiles("/searchdata");
        dataExplorerTile.open();
        WebElement dataExplorerTileElement = Bot.driver().findElement(By.className("js-hover"));
        tileHover(dataExplorerTileElement, "js-hover");
    }
}
