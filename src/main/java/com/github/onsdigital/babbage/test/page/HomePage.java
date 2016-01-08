package com.github.onsdigital.babbage.test.page;

import com.github.onsdigital.babbage.test.page.base.BabbagePage;
import com.github.webdriverextensions.Bot;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class HomePage extends BabbagePage {


    private static final String A_TO_Z_HREF = "a[href*='/atoz?az=%s";
    private static final String RGB_REGEX =  "rgb\\([0-9]{1,3}, ?[0-9]{1,3}, ?[0-9]{1,3}\\)";

    public HomePage() {
        super("");
    }

    @Override
    public void assertIsOpen(Object... objects) throws AssertionError {
        super.assertIsOpen(objects);
    }

    public void clickAtoZ(String character) {
        String searchTerm = String.format(A_TO_Z_HREF, character);
        Bot.driver().findElement(By.cssSelector(searchTerm)).click();
    }

    public WebElement getByCss(String cssValue) {
        return Bot.driver().findElement(By.cssSelector(cssValue));
    }

    public WebElement getAtoZ(final char alpha) {
        String searchTerm = String.format(A_TO_Z_HREF, alpha);
        return Bot.driver().findElement(By.cssSelector(searchTerm));
    }
 }

