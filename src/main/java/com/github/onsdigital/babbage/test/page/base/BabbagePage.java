package com.github.onsdigital.babbage.test.page.base;

import com.github.onsdigital.babbage.test.Configuration;
import com.github.webdriverextensions.Bot;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.Color;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.github.webdriverextensions.Bot.driver;
import static org.junit.Assert.fail;

/**
 * Base class for all page objects that belong to Babbage.
 * Deals with page components common to all pages such as search and breadcrumb.
 */
public abstract class BabbagePage extends PageObject {

	private static final String RGB_REGEX = "rgb\\([0-9]{1,3}, ?[0-9]{1,3}, ?[0-9]{1,3}\\)";

	private String uri;

	// the search box
	@FindBy(id = "nav-search")
	public WebElement search;

	// The prototype modal box continue button
	@FindBy(className = "btn-modal-continue")
	public WebElement modalContinue;

	public BabbagePage(String uri) {
		this.uri = uri;
	}

	public String getUri() {
		return uri;
	}

	public URL getAbsoluteUri() throws MalformedURLException {
		return new URL(Configuration.getBabbageUri(), uri);
	}

	/**
	 * Run a search for the given query.
	 *
	 * @param query - The query to search for.
	 * @return - the search results page.
	 */
	public void search(String query) {
		Bot.type(query, search);
		search.submit();
	}

	@Override
	public void open(Object... objects) {
		try {
			System.out.println("BabbagePage.Open " + getAbsoluteUri().toString());
			open(getAbsoluteUri().toString());
			super.open();
			checkForPrototypeModal();
			assertIsOpen();
		} catch (MalformedURLException e) {
			fail();
		}

	}

	@Override
	public void assertIsOpen(Object... objects) throws AssertionError {
		Bot.assertIsDisplayed(search);
	}

	private void checkForPrototypeModal() {
		try {
			modalContinue.click();
			WebDriverWait wait = new WebDriverWait(Bot.driver(), 5);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("btn-modal-continue"))); //wait until modal closed to continue
		} catch (NoSuchElementException exception) {
			// do nothing - there is no modal to close.
		}
	}


	public Color toColor(WebElement element) {
		String value = element.getCssValue("background");
		Matcher matcher = Pattern.compile(RGB_REGEX).matcher(value);
		matcher.find();
		String rgbStr = matcher.group(0);
		String[] temp = rgbStr.replace("rgb(", "").replace(")", "").split(",");
		return new Color(
				Integer.parseInt(temp[0].trim()),
				Integer.parseInt(temp[1].trim()),
				Integer.parseInt(temp[2].trim())
		);
	}

	public static void ScrollTo(WebElement element, Integer offset) {
		//TODO get add offset option instead of hard coding '112'
		Integer elementPosition;

		// get scroll position of button - check if custom offset argument being used
		if (offset != null) {
			elementPosition = element.getLocation().getY() - offset; // scroll position with custom offset
		} else {
			elementPosition = element.getLocation().getY(); // scroll position without any offset
		}

		String jsScroll = String.format("window.scroll(0, %s)", elementPosition); // create js scroll function
		((JavascriptExecutor) Bot.driver()).executeScript(jsScroll); //run js scroll function
	}

	public void hoverOverElement(WebElement target) {
		Actions action = new Actions(driver());
		action.moveToElement(target);
		action.perform();
	}
}
