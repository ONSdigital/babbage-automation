package com.github.onsdigital.babbage.test.page.base;

import com.github.onsdigital.babbage.test.Configuration;
import com.github.webdriverextensions.Bot;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.fail;

/**
 * Base class for all page objects that belong to Babbage.
 * Deals with page components common to all pages such as search and breadcrumb.
 */
public abstract class BabbagePage extends PageObject {

    private String uri;

    // the search box
    @FindBy(id = "nav-search")
    public WebElement search;

    // The prototype modal box continue button
    @FindBy(className = "btn-modal-continue")
    public WebElement modalContinue;

    public BabbagePage(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public URL getAbsoluteUri() throws MalformedURLException {
        return new URL(Configuration.getBabbageUri(), uri);
    }

    /**
     * Run a search for the given query.
     *
     * @param query - The query to search for.
     * @return - the search results page.
     */
    public void search(String query) {
        Bot.type(query, search);
        search.submit();
    }

    @Override
    public void open(Object... objects) {
        try {
            System.out.println("BabbagePage.Open " + getAbsoluteUri().toString());
            Bot.open(getAbsoluteUri().toString());
            super.open();
            checkForPrototypeModal();
            assertIsOpen();
        } catch (MalformedURLException e) {
            fail();
        }

    }

    @Override
    public void assertIsOpen(Object... objects) throws AssertionError {
        Bot.assertIsDisplayed(search);
    }

    private void checkForPrototypeModal() {
        try {
            modalContinue.click();
            WebDriverWait wait = new WebDriverWait(Bot.driver(), 5);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("btn-modal-continue"))); //wait until modal closed to continue
        } catch (NoSuchElementException exception) {
            // do nothing - there is no modal to close.
        }
    }
}
