package com.github.onsdigital.babbage.test;

import com.github.onsdigital.babbage.test.page.HomePage;
import com.github.webdriverextensions.Bot;
import org.junit.Test;

import static com.github.webdriverextensions.Bot.assertCurrentUrlContains;

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
}
