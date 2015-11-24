package com.github.onsdigital.babbage.test;

import com.github.onsdigital.babbage.test.page.HomePage;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;

import java.net.MalformedURLException;
import java.net.URL;

public class BrowserTestBase {

    protected WebDriver driver;

    @Before
    public void setUp() throws Exception {
        //driver = new FirefoxDriver();
        //driver = new HtmlUnitDriver(true);

        String browserStackUrl = Configuration.getBrowserStackUrl();

        if (StringUtils.isNotBlank(browserStackUrl)) {

            // todo: read different capabilities from json config file.
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("browser", "Chrome");
            caps.setCapability("browser_version", "31.0");
            caps.setCapability("os", "Windows");
            caps.setCapability("os_version", "7");
            caps.setCapability("resolution", "1600x1200");
            caps.setCapability("browserstack.debug", "true");

            try {
                driver = new RemoteWebDriver(new URL(browserStackUrl), caps);
                driver.manage().window().setSize(new Dimension(1600,1200));
            } catch (MalformedURLException e) {
                throw new Error("Could not connect to BrowserStack with the given URL: " + browserStackUrl, e);
            }
        } else {

            final String chromeDriverUrl = "http://localhost:9515"; // standard port for chrome driver
            try {
                ChromeOptions options = new ChromeOptions();
                DesiredCapabilities desiredCapabilities = DesiredCapabilities.chrome();
                desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
                driver = new RemoteWebDriver(new URL(chromeDriverUrl), desiredCapabilities);
            } catch (MalformedURLException e) {
                throw new Error("Could not connect to ChromeDriver with the given URL: " + chromeDriverUrl, e);
            } catch (UnreachableBrowserException exception) {
                throw new Error("Could not find browser, are you running chrome driver?", exception);
            }

            driver.manage().window().setSize(new Dimension(1600,1200));
        }
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
