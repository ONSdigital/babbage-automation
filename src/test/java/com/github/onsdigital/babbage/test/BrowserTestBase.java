package com.github.onsdigital.babbage.test;

import com.github.webdriverextensions.Bot;
import com.github.webdriverextensions.WebDriverExtensionsContext;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class BrowserTestBase {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return getParameters();
    }

    @Parameterized.Parameter
    public DesiredCapabilities desiredCapabilities;

    public static Collection<Object[]> getParameters() {
        String browserStackUrl = Configuration.getBrowserStackUrl();

        // if the browser stack url is defined run against multiple browsers, else just run in chrome in development.
        if (StringUtils.isNotBlank(browserStackUrl)) {
            return Arrays.asList(new Object[][]{
                    {DesiredCapabilities.firefox()}, {DesiredCapabilities.chrome()}
            });
        } else {
            return Arrays.asList(new Object[][]{
                    {DesiredCapabilities.chrome()}
            });
        }
    }

    @Before
    public void setUp() throws Exception {
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

            final String chromeDriverUrl = "http://localhost:9515"; // standard port for chrome driver
            try {
                ChromeOptions options = new ChromeOptions();
                DesiredCapabilities desiredCapabilities = DesiredCapabilities.chrome();
                desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
                WebDriverExtensionsContext.setDriver(new RemoteWebDriver(new URL(chromeDriverUrl), desiredCapabilities));
            } catch (MalformedURLException e) {
                throw new Error("Could not connect to ChromeDriver with the given URL: " + chromeDriverUrl, e);
            } catch (UnreachableBrowserException exception) {
                throw new Error("Could not find browser, are you running chrome driver?", exception);
            }

            Bot.driver().manage().window().setSize(new Dimension(1600, 1200));
        }
    }

    public static void main(String[] args) throws IOException {

        // https://code.google.com/p/selenium/wiki/DesiredCapabilities
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("browser", "Chrome");
        caps.setCapability("browser_version", "31.0");
        caps.setCapability("os", "Windows");
        caps.setCapability("os_version", "7");
        caps.setCapability("resolution", "1600x1200");
        caps.setCapability("browserstack.debug", "true");

        final String json = new GsonBuilder().setPrettyPrinting().create().toJson(caps);

        FileUtils.write(new File("src/test/resources/config.json"), json);


    }
}
