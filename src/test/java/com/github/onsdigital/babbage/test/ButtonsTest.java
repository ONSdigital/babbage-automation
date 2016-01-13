package com.github.onsdigital.babbage.test;

import com.github.onsdigital.babbage.test.base.BrowserTestBase;
import com.github.onsdigital.babbage.test.page.Buttons;
import com.github.onsdigital.babbage.test.page.base.BabbagePage;
import com.github.webdriverextensions.Bot;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Iterator;
import java.util.List;

/**
 * Created by crispin on 11/01/2016.
 */
public class ButtonsTest extends BrowserTestBase {

    @Test
    public void buttonHoverPublication() throws InterruptedException {
        Buttons page = new Buttons("/publications");
        page.open(); //open publications list from root
        WebDriver driver = Bot.driver();

        WebElement publicationLink = driver.findElement(By.cssSelector("li.search-results__item a"));
        publicationLink.click();

        // Get height of sticky toc for scroll offset
        Buttons publication = new Buttons("");
        Integer stickyTocHeight = publication.StickyTocHeight();

        // On publication - hover over each primary button and confirm colour change
        List<WebElement> allCTAs = driver.findElements(By.className("btn--primary"));
        Iterator<WebElement> i = allCTAs.iterator();
        while (i.hasNext()) {
            WebElement CTA = i.next();
            if (CTA.isDisplayed()) {
                // String buttonText = CTA.getText();
                String preHover = CTA.getCssValue("background-color");
                BabbagePage.ScrollTo(CTA, stickyTocHeight);

                hoverOverElement(CTA);
                String duringHover = CTA.getCssValue("background-color");

//                System.out.println("Before hover: " + preHover);
//                System.out.println("After hover: " + duringHover);

                Assert.assertNotEquals("No colour change on button hover", preHover, duringHover);;


                // TODO if button, not anchor, then click and check file download. See http://ardesco.lazerycode.com/index.php/2012/07/how-to-download-files-with-selenium-and-why-you-shouldnt/
//                String CTAelement = CTA.getTagName();
//                if (CTAelement.equals("button")) {
//                  do file check
//
//                }
            }
        }
    }
}
