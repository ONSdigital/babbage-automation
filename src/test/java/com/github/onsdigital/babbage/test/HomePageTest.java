package com.github.onsdigital.babbage.test;

import com.github.onsdigital.babbage.test.page.SearchResultsPage;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HomePageTest extends BrowserTestBase {

    @Test
    public void searchFromHomePage(){
        SearchResultsPage resultsPage = fromHome().search("economy");
        System.out.println(driver.getCurrentUrl());
        assertTrue(driver.getCurrentUrl().contains("economy"));
    }
}
