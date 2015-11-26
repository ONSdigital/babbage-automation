package com.github.onsdigital.babbage.test.base;

import com.github.onsdigital.babbage.test.Configuration;
import com.github.webdriverextensions.Bot;
import com.github.webdriverextensions.WebDriverExtensionsContext;
import com.github.webdriverextensions.internal.junitrunner.DriverPathLoader;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class BrowserTestBase {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return getParameters();
    }

    @Parameterized.Parameter
    public DesiredCapabilities desiredCapabilities;


    static {
        // invoke the framework method to set the driver paths as expected.
        DriverPathLoader.loadDriverPaths(null);
    }

    public static Collection<Object[]> getParameters() {
        String browserStackUrl = Configuration.getBrowserStackUrl();

        // if the browser stack url is defined run against multiple browsers, else just run in chrome in development.
        if (StringUtils.isNotBlank(browserStackUrl)) {
            return getBrowserStackConfiguration();
        } else {
            return Arrays.asList(new Object[][]{
                    {DesiredCapabilities.chrome()}
            });
        }
    }

    private static Collection<Object[]> getBrowserStackConfiguration() {
        List<Object[]> capabilities = new ArrayList<>();

        DesiredCapabilities chromeWindows = new DesiredCapabilities();
        chromeWindows.setCapability("browser", "Chrome");
        chromeWindows.setCapability("browser_version", "31.0");
        chromeWindows.setCapability("os", "Windows");
        chromeWindows.setCapability("os_version", "7");
        chromeWindows.setCapability("browserstack.debug", "true");
        capabilities.add(new Object[]{chromeWindows});

//        DesiredCapabilities ipad = new DesiredCapabilities();
//        ipad.setCapability("browserName", "iPad");
//        ipad.setCapability("platform", "MAC");
//        ipad.setCapability("device", "iPad Air");
//        ipad.setCapability("browserstack.debug", "true");
//        capabilities.add(new Object[]{ipad});
//
//        DesiredCapabilities iphone = new DesiredCapabilities();
//        iphone.setCapability("browserName", "iPhone");
//        iphone.setCapability("platform", "MAC");
//        iphone.setCapability("device", "iPhone 5");
//        iphone.setCapability("browserstack.debug", "true");
//        capabilities.add(new Object[]{iphone});
//
//        DesiredCapabilities android = new DesiredCapabilities();
//        android.setCapability("browserName", "android");
//        android.setCapability("platform", "ANDROID");
//        android.setCapability("device", "Samsung Galaxy S5");
//        android.setCapability("browserstack.debug", "true");
//        capabilities.add(new Object[]{android});

        DesiredCapabilities ieWindows = new DesiredCapabilities();
        ieWindows.setCapability("browser", "IE");
        ieWindows.setCapability("browser_version", "9.0");
        ieWindows.setCapability("os", "Windows");
        ieWindows.setCapability("os_version", "7");
        ieWindows.setCapability("browserstack.debug", "true");
        capabilities.add(new Object[]{ieWindows});

        DesiredCapabilities safariOsx = new DesiredCapabilities();
        safariOsx.setCapability("browser", "safari");
        safariOsx.setCapability("os", "OS X");
        safariOsx.setCapability("os_version", "Yosemite");
        safariOsx.setCapability("browserstack.debug", "true");
        capabilities.add(new Object[]{safariOsx});

        return capabilities;
    }

    @Before
    public void setUp() throws Exception {
        String browserStackUrl = Configuration.getBrowserStackUrl();

        if (StringUtils.isNotBlank(browserStackUrl)) {
            try {
                WebDriverExtensionsContext.setDriver(new RemoteWebDriver(new URL(browserStackUrl), desiredCapabilities));
            } catch (MalformedURLException e) {
                throw new Error("Could not connect to BrowserStack with the given URL: " + browserStackUrl, e);
            }
        } else {
            WebDriverExtensionsContext.setDriver(new ChromeDriver(desiredCapabilities));
            Bot.driver().manage().window().setSize(new Dimension(1600, 1200));
        }
    }

    @After
    public void tearDown() throws Exception {
        Bot.driver().quit();
        WebDriverExtensionsContext.removeDriver();
    }
}
