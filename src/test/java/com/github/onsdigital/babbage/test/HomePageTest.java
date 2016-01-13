package com.github.onsdigital.babbage.test;

import com.github.onsdigital.babbage.test.base.BrowserTestBase;
import com.github.onsdigital.babbage.test.page.HomePage;
import com.github.webdriverextensions.Bot;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.awt.*;

import static com.github.webdriverextensions.Bot.assertCurrentUrlContains;
import static com.github.webdriverextensions.Bot.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

public class HomePageTest extends BrowserTestBase {

    HomePage homePage = new HomePage();

    @Test
    public void searchFromHomePage() {
        Bot.open(homePage);
        homePage.search("economy");
        System.out.println(Bot.driver().getCurrentUrl());
        assertCurrentUrlContains("economy");
    }

    @Test
    public void searchCdidFromHomePage() {
        Bot.open(homePage);
        homePage.search("d7g7");
        System.out.println(Bot.driver().getCurrentUrl());
        assertCurrentUrlContains("/timeseries/d7g7");
    }

    @Test
    public void testAtoZFromHomePage() {
        Bot.open(homePage);
        homePage.clickAtoZ("d");
        assertCurrentUrlContains("/atoz?az=d");
    }

    @Test
    public void testAtoZHover() throws Exception {
        homePage.open();
        WebElement aTozItem = homePage.getAtoZ('x');

        Color preHover = homePage.toColor(aTozItem);

        Actions action = new Actions(Bot.driver());
        action.moveToElement(aTozItem);
        action.perform();

        WebElement aTozHover = homePage.getAtoZ('x');
        Color postHover = homePage.toColor(aTozHover);

        assertThat("AtoZ Colour should change for hover event.", preHover, is(not(postHover)));
    }

}
