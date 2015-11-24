package com.github.onsdigital.babbage.test.page;

import com.github.onsdigital.babbage.test.page.base.PageObject;
import com.github.webdriverextensions.Bot;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class HomePage extends PageObject {

    @FindBy(id = "nav-search")
    WebElement search;

    @FindBy(className = "btn-modal-continue")
    WebElement modalContinue;

    private void checkForPrototypeModal() {
        try {
            modalContinue.click();
        } catch (NoSuchElementException exception) {
            // do nothing - there is no modal to close.
        }
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
        super.open();
        checkForPrototypeModal();
        assertIsOpen();
    }

    @Override
    public void assertIsOpen(Object... objects) throws AssertionError {
        Bot.assertIsDisplayed(search);
    }
}

