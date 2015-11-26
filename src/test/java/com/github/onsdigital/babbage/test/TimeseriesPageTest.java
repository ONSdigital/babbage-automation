package com.github.onsdigital.babbage.test;

import com.github.onsdigital.babbage.test.base.BrowserTestBase;
import com.github.onsdigital.babbage.test.page.TimeseriesPage;
import com.github.webdriverextensions.Bot;
import org.junit.Test;

public class TimeseriesPageTest extends BrowserTestBase {

    TimeseriesPage page = new TimeseriesPage("economy/inflationandpriceindices/timeseries/d7g7");

    @Test
    public void timeseriesPage() {
        Bot.open(page);
//        page.clickTableView();
//        page.clickChartView();
    }
}
