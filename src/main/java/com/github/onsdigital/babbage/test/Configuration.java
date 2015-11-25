package com.github.onsdigital.babbage.test;

import org.apache.commons.lang3.StringUtils;

public class Configuration {
    //private static final String DEFAULT_BABBAGE_URL = "http://localhost:8080";
    private static final String DEFAULT_BABBAGE_URL = "https://beta.ons.gov.uk";

    public static String getBabbageUrl() {
        return StringUtils.defaultIfBlank(getValue("BABBAGE_URL"), DEFAULT_BABBAGE_URL);
    }

    public static String getBrowserStackUrl() {
        return getValue("BROWSERSTACK_URL");
    }

    static String getValue(String key) {
        return StringUtils.defaultIfBlank(System.getProperty(key), System.getenv(key));
    }
}
