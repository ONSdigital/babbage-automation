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
            return Arrays.asList(new Object[][]{
                    getBrowserStackConfiguration()
            });
        } else {
            return Arrays.asList(new Object[][]{
                    {DesiredCapabilities.chrome()}
            });
        }
    }

    private static Object[] getBrowserStackConfiguration() {
        List<DesiredCapabilities> capabilities = new ArrayList<>();

        DesiredCapabilities ieCapabilities = new DesiredCapabilities();
        ieCapabilities.setCapability("browser", "Chrome");
        ieCapabilities.setCapability("browser_version", "31.0");
        ieCapabilities.setCapability("os", "Windows");
        ieCapabilities.setCapability("os_version", "7");
        ieCapabilities.setCapability("resolution", "1600x1200");
        ieCapabilities.setCapability("browserstack.debug", "true");
        capabilities.add(ieCapabilities);

        DesiredCapabilities fireFoxWindows = new DesiredCapabilities();
        fireFoxWindows.setCapability("browser", "IE");
        ieCapabilities.setCapability("browser_version", "9.0");
        fireFoxWindows.setCapability("os", "Windows");
        fireFoxWindows.setCapability("os_version", "7");
        fireFoxWindows.setCapability("resolution", "1600x1200");
        fireFoxWindows.setCapability("browserstack.debug", "true");
        capabilities.add(fireFoxWindows);

        DesiredCapabilities safariOsx = new DesiredCapabilities();
        safariOsx.setCapability("browser", "safari");
        safariOsx.setCapability("os", "OS X");
        safariOsx.setCapability("os_version", "Yosemite");
        safariOsx.setCapability("resolution", "1600x1200");
        safariOsx.setCapability("browserstack.debug", "true");
        capabilities.add(safariOsx);

        return capabilities.toArray();
    }

    @Before
    public void setUp() throws Exception {
        System.out.println("BrowserTestBase.setup");
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
                WebDriverExtensionsContext.setDriver(new RemoteWebDriver(new URL(browserStackUrl), caps));
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
    }

    //    public static void main(String[] args) throws IOException {
//
//        // https://code.google.com/p/selenium/wiki/DesiredCapabilities
//        DesiredCapabilities caps = new DesiredCapabilities();
//        caps.setCapability("browser", "Chrome");
//        caps.setCapability("browser_version", "31.0");
//        caps.setCapability("os", "Windows");
//        caps.setCapability("os_version", "7");
//        caps.setCapability("resolution", "1600x1200");
//        caps.setCapability("browserstack.debug", "true");
//
//        final String json = new GsonBuilder().setPrettyPrinting().create().toJson(caps);
//
//        FileUtils.write(new File("src/test/resources/config.json"), json);
//
//
//    }
}
