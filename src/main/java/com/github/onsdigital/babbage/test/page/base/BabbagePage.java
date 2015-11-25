package com.github.onsdigital.babbage.test.page.base;

import com.github.onsdigital.babbage.test.Configuration;
import com.github.webdriverextensions.Bot;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.nio.file.Paths;

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

    public String getAbsoluteUri() {
        return Paths.get(Configuration.getBabbageUrl()).resolve(uri).toString();
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
        System.out.println("BabbagePage.Open " + getAbsoluteUri());
        Bot.open(getAbsoluteUri());
        super.open();
        checkForPrototypeModal();
        assertIsOpen();
    }

    @Override
    public void assertIsOpen(Object... objects) throws AssertionError {
        Bot.assertIsDisplayed(search);
    }

    private void checkForPrototypeModal() {
        try {
            modalContinue.click();
        } catch (NoSuchElementException exception) {
            // do nothing - there is no modal to close.
        }
    }
}
