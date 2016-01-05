package com.github.onsdigital.babbage.test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.apache.commons.lang3.StringUtils.defaultIfBlank;

public class Configuration {
    private static final String DEFAULT_BABBAGE_URL = "http://localhost:8080";
//    private static final String DEFAULT_BABBAGE_URL = "https://beta.ons.gov.uk";
//    private static final String DEFAULT_BABBAGE_URL = "http://stats:Magic5yf&Roundabout@develop.carb.onl";

    public static URL getBabbageUri() throws MalformedURLException {
        return new URL(defaultIfBlank(getValue("BABBAGE_URL"), DEFAULT_BABBAGE_URL));
    }

    public static String getBrowserStackUrl() {
        return getValue("BROWSERSTACK_URL");
    }

    static String getValue(String key) {
        return defaultIfBlank(System.getProperty(key), System.getenv(key));
    }
}
