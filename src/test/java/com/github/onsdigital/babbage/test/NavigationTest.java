package com.github.onsdigital.babbage.test;

import com.github.onsdigital.babbage.test.base.BrowserTestBase;
import com.github.onsdigital.babbage.test.page.HomePage;
import org.junit.Test;

/**
 * Tests to browse around the website to test the general site health and navigation.
 */
public class NavigationTest extends BrowserTestBase {

    @Test
    public void navigate() {
        HomePage home = new HomePage();
        home.open();
    }
}
