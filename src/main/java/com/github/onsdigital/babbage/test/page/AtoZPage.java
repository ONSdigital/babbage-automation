package com.github.onsdigital.babbage.test.page;

import com.github.onsdigital.babbage.test.AtoZSearchItem;
import com.github.onsdigital.babbage.test.page.base.BabbagePage;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

import static com.github.webdriverextensions.Bot.driver;

public class AtoZPage extends BabbagePage {

	static final String A_TO_Z_LABEL_XPATH = "//label[text()='%s']";

	static final String DEFAULT_SEARCH_RESULT_TITLE = "[0-9]+ statistical bulletins";
	static String FILTERED_SEARCH_REGEX = DEFAULT_SEARCH_RESULT_TITLE + " containing \'%s\'";
	static final char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	public static final Color ATOZ_NON_CLICKABLE_COLOR = new Color(208, 210, 211);
	public static final Color ATOZ_CLICKABLE_PRE_HOVER_COLOR = new Color(88, 89, 91);
	public static final Color ATOZ_CLICKABLE_HOVER_COLOR = new Color(50, 49, 50);

	@FindBy(id = "input-keywords")
	private WebElement keywordSearch;

	@FindBy(className = "search-page__results-text")
	private WebElement searchResultSummary;

	public AtoZPage(String uri) {
		super(uri);
	}

	public void enterKeyWordFilter(final String searchTerm) {
		keywordSearch.sendKeys(searchTerm);
	}

	/**
	 * Enters a key into the filter box.
	 */
	public void enterKeyWordFilter(AtoZSearchItem atoZSearchItem) {
		keywordSearch.sendKeys(atoZSearchItem.getKeyword());
		keywordSearch.submit();

		final String regex = String.format(FILTERED_SEARCH_REGEX, atoZSearchItem.getKeyword());

		new WebDriverWait(driver(), 30).until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver input) {
				String searchResultText = getSearchResultSummaryText().getText();
				return Pattern.compile(regex).matcher(searchResultText).matches();
			}
		});
	}

	public void useAtoZFilter(final char filterKey) throws Exception {
		String xpathToTarget = String.format(A_TO_Z_LABEL_XPATH, filterKey);
		// wait for the element to clickable
		new WebDriverWait(driver(), 30).until(ExpectedConditions.elementToBeClickable(By.xpath(xpathToTarget)));
		driver().findElement(By.xpath(String.format(A_TO_Z_LABEL_XPATH, filterKey))).click();

		new WebDriverWait(driver(), 30).until(ExpectedConditions.refreshed(
				ExpectedConditions.elementToBeClickable(By.xpath(xpathToTarget))));

		driver().findElement(By.xpath(String.format(A_TO_Z_LABEL_XPATH, filterKey))).click();
		new WebDriverWait(driver(), 30).until(ExpectedConditions.elementToBeClickable(By.xpath(xpathToTarget)));
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

	/**
	 * @return the WebElement for the A-to-Z label specified by the character.
	 */
	public WebElement getAToZLabel(final char character) {
		String xpath = String.format(A_TO_Z_LABEL_XPATH, character);
		new WebDriverWait(driver(), 10).until(
				ExpectedConditions.refreshed(
						ExpectedConditions.elementToBeClickable(
								By.xpath(xpath))));
		return driver().findElement(By.xpath(String.format(A_TO_Z_LABEL_XPATH, character)));
	}

	public WebElement getAToZInput(final char chatacter) {
		return driver().findElement(By.id("radio-" + chatacter));
	}

	public String getResultTextRegex(String filter) {
		if (filter == null) {
			return DEFAULT_SEARCH_RESULT_TITLE;
		}
		return String.format(FILTERED_SEARCH_REGEX, filter);
	}

	public AtoZSearchItem getAvailableAtoZSearch() throws Exception {
		List<AtoZSearchItem> results = getAtoZSearchResultsForKeyword(null);
		return results.get(new Random().nextInt(results.size()));
	}

	/**
	 * @return returns a list of searchable result for the specified keyword.
	 */
	public List<AtoZSearchItem> getAtoZSearchResultsForKeyword(String keyword) throws Exception {
		if (StringUtils.isEmpty(keyword)) {
			open();
		} else {
			open(getAbsoluteUri().toString() + "?query=" + keyword);
		}

		List<WebElement> list = driver()
				.findElement(By.className("search-results"))
				.findElement(By.tagName("ul"))
				.findElements(By.className("search-results__item"));

		List<AtoZSearchItem> atoZSearchItems = new ArrayList<>();

		for (WebElement item : list) {
			AtoZSearchItem.Builder builder = new AtoZSearchItem.Builder();
			builder.title(item.findElement(By.className("search-results__title")).getText())
					.summary(item.findElement(By.className("search-results__summary")).getText())
					.keywords(item.findElement(By.className("search-results__keywords")).getText());

			atoZSearchItems.add(builder.build());
		}
		return atoZSearchItems;
	}

	/**
	 * performs a page scrape after searching for a keyword and returns a list of clickable A-To-Z filters.
	 */
	public List<Character> getClickableAToZFiltersFromSearchResults(List<AtoZSearchItem> searchItems) throws Exception {
		Set<Character> keywordFilters = new HashSet<>();
		for (AtoZSearchItem searchItem : searchItems) {
			keywordFilters.add(searchItem.getaToZFilter());
		}
		return new ArrayList<>(keywordFilters);
	}

	/**
	 * performs a page scrape after searching for a keyword and returns a list of non clickable A-To-Z filters.
	 */
	public List<Character> getNonClickableAToZFiltersFromSearchResults(List<AtoZSearchItem> searchItems) throws Exception {
		List<Character> result = new ArrayList<>();
		List<Character> keywordFilters = getClickableAToZFiltersFromSearchResults(searchItems);

		for (char alpha : ALPHABET) {
			if (!keywordFilters.contains(alpha)) {
				result.add(alpha);
			}
		}
		return result;
	}
}
