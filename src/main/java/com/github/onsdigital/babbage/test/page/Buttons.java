package com.github.onsdigital.babbage.test.page;

import com.github.onsdigital.babbage.test.page.base.BabbagePage;
import com.github.webdriverextensions.Bot;
import org.apache.http.client.methods.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by crispin on 11/01/2016.
 */
public class Buttons extends BabbagePage {
    public Buttons(String url) {
        super(url);
    }

    public Integer StickyTocHeight() {
        WebDriver driver = Bot.driver();
        WebDriverWait wait = new WebDriverWait(driver, 5);

        WebElement stickyTocTrigger = driver.findElement(By.className("js-sticky-toc__trigger"));
        BabbagePage.ScrollTo(stickyTocTrigger, -1);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("table-of-contents--sticky__wrap")));
        WebElement stickyTocWrap = driver.findElement(By.className("table-of-contents--sticky__wrap"));
        String heightString = stickyTocWrap.getCssValue("height").replaceAll("[^0-9]","") ; // remove 'px' from string
        Integer stickyTocHeight = Integer.parseInt(heightString);
        return stickyTocHeight;
    }
}


