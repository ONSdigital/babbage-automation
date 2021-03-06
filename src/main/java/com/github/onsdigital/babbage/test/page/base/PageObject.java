package com.github.onsdigital.babbage.test.page.base;

import com.github.webdriverextensions.Bot;
import com.github.webdriverextensions.WebDriverExtensionFieldDecorator;
import com.github.webdriverextensions.WebPage;
import org.openqa.selenium.support.PageFactory;

/**
 * This is the base PageObject class that all page objects inherit.
 */
public abstract class PageObject extends WebPage {

    @Override
    public void open(Object... objects) {
        // initialise up all the WebElement fields annotated with @FindBy
        PageFactory.initElements(new WebDriverExtensionFieldDecorator(Bot.driver()), this);
    }
}
