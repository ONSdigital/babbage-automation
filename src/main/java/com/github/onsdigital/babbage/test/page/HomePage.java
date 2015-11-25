package com.github.onsdigital.babbage.test.page;

import com.github.onsdigital.babbage.test.Configuration;
import com.github.onsdigital.babbage.test.page.base.BabbagePage;
import com.github.onsdigital.babbage.test.page.base.PageObject;
import com.github.webdriverextensions.Bot;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class HomePage extends BabbagePage {

    public HomePage() {
        super("");
    }

    @Override
    public void assertIsOpen(Object... objects) throws AssertionError {
        super.assertIsOpen(objects);
    }
}

