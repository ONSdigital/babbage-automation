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
        assertCurrentUrlContains("ons");
        homePage.search("economy");
        System.out.println(Bot.driver().getCurrentUrl());
        assertCurrentUrlContains("economy");
    }
}
