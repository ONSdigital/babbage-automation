package com.github.onsdigital.babbage.test.page;

import com.github.onsdigital.babbage.test.page.base.PageObject;
import com.github.onsdigital.babbage.test.page.base.PageObjectException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends PageObject {

    By searchLocator = By.id("nav-search");

    WebElement search;

    public HomePage(WebDriver driver) {
        super(driver);
        checkForPrototypeModal();
        initialisePage();
    }

    private void checkForPrototypeModal() {
        try {
            WebElement modalContinue = find(By.className("btn-modal-continue"));
            modalContinue.click();
        } catch (NoSuchElementException exception) {
            // do nothing - there is no modal to close.
        }

    }

    /**
     * Check the expected elements are located in the page.
     */
    protected HomePage initialisePage() {
        try {
            search = waitAndFind(searchLocator);
        } catch (NoSuchElementException exception) {
            throw new PageObjectException("Failed to recognise the " + this.getClass().getSimpleName() + " contents.", exception);
        }
        return this;
    }

    /**
     * Run a search for the given query.
     *
     * @param query - The query to search for.
     * @return - the search results page.
     */
    public SearchResultsPage search(String query) {
        search.sendKeys(query);
        search.submit();
        return new SearchResultsPage(driver);
    }
}

