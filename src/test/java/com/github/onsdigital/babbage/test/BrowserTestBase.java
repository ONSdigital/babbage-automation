package com.github.onsdigital.babbage.test;

import com.github.onsdigital.babbage.test.page.HomePage;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BrowserTestBase {

    protected WebDriver driver;

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        //driver = new HtmlUnitDriver(true);
    }

    @After
    public void tearDown() throws Exception {
        driver.close();
    }

    public HomePage fromHome() {
        driver.get(Configuration.getBabbageUrl());
        return new HomePage(driver);
    }
}
