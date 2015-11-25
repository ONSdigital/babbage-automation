package com.github.onsdigital.babbage.test.page;

import com.github.onsdigital.babbage.test.page.base.BabbagePage;
import com.github.webdriverextensions.Bot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TimeseriesPage extends BabbagePage {

    @FindBy(id = "type-chart")
    WebElement chart;

    @FindBy(id = "type-table")
    WebElement table;

    public TimeseriesPage(String uri) {
        super(uri);
    }

    @Override
    public void assertIsOpen(Object... objects) throws AssertionError {
        super.assertIsOpen(objects);

        Bot.assertCurrentUrlContains(this.getUri());
    }

    public void clickTableView() {
        table.click();
        Bot.assertHasClass("btn--secondary--active", table);
        Bot.assertHasNotClass("btn--secondary--active", chart);
    }

    public void clickChartView() {
        chart.click();
        Bot.assertHasClass("btn--secondary--active", chart);
        Bot.assertHasNotClass("btn--secondary--active", table);
    }
}
