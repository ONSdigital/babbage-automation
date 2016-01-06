package com.github.onsdigital.babbage.test;

import com.github.onsdigital.babbage.test.base.BrowserTestBase;
import com.github.onsdigital.babbage.test.page.SearchResultsPage;
import com.github.webdriverextensions.Bot;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Iterator;
import java.util.List;

/**
 * Created by crispin on 05/01/2016.
 */
public class SearchResultsTest extends BrowserTestBase {
    String searchQuery = "?q=labour";

    SearchResultsPage page = new SearchResultsPage("search" + searchQuery);

    @Test
    public void SearchPublication() {
        WebDriver driver = Bot.driver();
        WebDriverWait wait = new WebDriverWait(driver, 5); //Pause for 5 seconds
        page.open();

        // Check bulletin content type filter
        WebElement BulletinCheckbox = waitAndFind(By.xpath("//*[@id=\"checkbox-bulletin\"]"));
        BulletinCheckbox.click();
        wait.until(ExpectedConditions.urlContains("filter=bulletin"));

        // Assert that results contains a bulletin
        String resultMeta = driver.findElement(By.className("search-results__meta")).getText();
        String resultsText = driver.findElement(By.className("search-page__results-text")).getText();
        Assert.assertTrue("No bulletins in search results", resultMeta.contains("statistical bulletin"));
        Assert.assertTrue("Results text doesn't match selected filter", resultsText.contains("statistical bulletin"));

        // Select article checkbox too
        WebElement ArticleCheckbox = waitAndFind(By.xpath("//*[@id=\"checkbox-article\"]"));
        ArticleCheckbox.click();
        wait.until(ExpectedConditions.urlContains("filter=article"));

        // Assert that results text contains articles too
        resultsText = driver.findElement(By.className("search-page__results-text")).getText();
        Assert.assertTrue("Results text doesn't contain 'article'", resultsText.contains("article"));
        Assert.assertTrue("Results text doesn't contain 'statistical bulletin'", resultsText.contains("statistical bulletin"));

        // Uncheck bulletin and check that results shows articles
        BulletinCheckbox = waitAndFind(By.xpath("//*[@id=\"checkbox-bulletin\"]"));
        BulletinCheckbox.click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.className("search-page__results-text"), "articles"));

        // Go through each result and check it is an article
        List<WebElement> resultsList = driver.findElements(By.className("search-results__meta"));
        Iterator<WebElement> i = resultsList.iterator();
        while (i.hasNext()) {
            WebElement result = i.next();
            String itemText = result.getText();
            Assert.assertTrue("Not all results are a spreadsheet", itemText.contains("article"));
            System.out.println("Item metadata: " + "'" + itemText + "'");
        }

        System.out.println("Successfully navigated to " + driver.getCurrentUrl());

    }

    @Test
    public void SearchData() {
        WebDriver driver = Bot.driver();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        page.open();

        // Check time series data filter
        WebElement timeSeriesCheckbox = waitAndFind(By.xpath("//*[@id=\"checkbox-timeseries\"]"));
        timeSeriesCheckbox.click();
        wait.until(ExpectedConditions.urlContains("filter=single_time_series"));

        // Assert that results contain time series data
        String resultMeta = driver.findElement(By.className("search-results__meta")).getText();
        Assert.assertTrue("No time series show in results", resultMeta.contains("time series data"));

        // Check spreadsheets and confirm that results text contains both content types
        WebElement spreadsheets = driver.findElement(By.xpath("//*[@id=\"checkbox-dataset\"]"));
        spreadsheets.click();
        wait.until(ExpectedConditions.urlContains("filter=large_dataset"));
        String resultsText = driver.findElement(By.className("search-page__results-text")).getText();
        Assert.assertTrue("Results text doesn't contain 'time series data'", resultsText.contains("time series data"));
        Assert.assertTrue("Results text doesn't contain 'spreadsheets'", resultsText.contains("spreadsheets"));

        // Uncheck time series data and check that results contains
        timeSeriesCheckbox = waitAndFind(By.xpath("//*[@id=\"checkbox-timeseries\"]"));
        timeSeriesCheckbox.click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.className("search-page__results-text"), "spreadsheets"));
        List<WebElement> resultsList = driver.findElements(By.className("search-results__meta")); // Find metadata on each result
        Iterator<WebElement> i = resultsList.iterator();
        while (i.hasNext()) { // Iterate through results and confirm that all results are spreadsheets
            WebElement result = i.next();
            String itemText = result.getText();
            Assert.assertTrue("Not all results are a spreadsheet", itemText.contains("spreadsheet"));
            System.out.println("Item metadata: " + "'" + itemText + "'");
        }
    }

    @Test
    public void SearchTabs() {
        // Click all three tabs and check URL
        WebDriver driver = Bot.driver();
        page.open();
        WebElement tabLinkData = waitAndFind(By.xpath("(//a[contains(@class, 'tab__link')])[2]")); // Data tab
        tabLinkData.click(); // Click tab
        Bot.assertCurrentUrlEndsWith("searchdata" + searchQuery); // Confirm click has worked and we're on the correct search endpoint
        WebElement tabLinkPublications = driver.findElement(By.xpath("(//a[contains(@class, 'tab__link')])[3]")); // Publications tab
        tabLinkPublications.click();
        Bot.assertCurrentUrlEndsWith("searchpublication" + searchQuery);
        WebElement tabLinkAll = driver.findElement(By.xpath("(//a[contains(@class, 'tab__link')])[1]")); // All tab
        tabLinkAll.click();
        Bot.assertCurrentUrlEndsWith("search" + searchQuery);
    }
}
