package com.github.onsdigital.babbage.test.page;

import com.github.onsdigital.babbage.test.page.base.BabbagePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.github.webdriverextensions.Bot.driver;

/**
 * Created by dave on 1/7/16.
 */
public class AtoZPage extends BabbagePage {

	static final String A_TO_Z_LABEL_XPATH = "//label[text()='%s']";

	@FindBy(id = "input-keywords")
	private WebElement keywordSearch;

	@FindBy(className = "search-page__results-text")
	private WebElement searchResultSummary;

	public AtoZPage(String uri) {
		super(uri);
	}

	public void enterKeyWordFilter(final String searchTerm) {
		keywordSearch.sendKeys(searchTerm);
		keywordSearch.submit();
	}

	public void useAtoZFilter(final char filterKey) throws Exception {
		String xpathToTarget = String.format(A_TO_Z_LABEL_XPATH, filterKey);
		// wait for the element to clickable
		new WebDriverWait(driver(), 10).until(ExpectedConditions.elementToBeClickable(By.xpath(xpathToTarget)));
		driver().findElement(By.xpath(String.format(A_TO_Z_LABEL_XPATH, filterKey))).click();
		new WebDriverWait(driver(), 10).until(ExpectedConditions.elementToBeClickable(By.xpath(xpathToTarget)));
	}

	public WebElement getSearchResultSummaryText() {
		return driver().findElement(By.className("search-page__results-text"));
	}

	public WebElement getAToZItem(final char chatacter) {
		return driver().findElement(By.xpath(String.format(A_TO_Z_LABEL_XPATH, chatacter)));
	}

	public void hoverOverAtoZ(final char character) {
		Actions action = new Actions(driver());
		action.moveToElement(getAToZItem(character));
		action.perform();
	}
}
