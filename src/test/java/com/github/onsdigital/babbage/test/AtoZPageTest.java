package com.github.onsdigital.babbage.test;

import com.github.onsdigital.babbage.test.base.BrowserTestBase;
import com.github.onsdigital.babbage.test.page.AtoZPage;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.awt.Color;
import java.util.List;

import static com.github.webdriverextensions.Bot.assertTextMatches;
import static com.github.webdriverextensions.Bot.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class AtoZPageTest extends BrowserTestBase {

	private static final String URI = "atoz";

	private AtoZPage aToZPage = new AtoZPage(URI);

	// TODO test failing intermittently with stale element error.
	@Ignore
	@Test
	public void testGoToAtoZ() throws Exception {
		AtoZSearchItem searchTarget = aToZPage.getAvailableAtoZSearch();
		aToZPage.open();
		assertTextMatches(aToZPage.getResultTextRegex(null), aToZPage.getSearchResultSummaryText());
		aToZPage.enterKeyWordFilter(searchTarget);
		aToZPage.useAtoZFilter(searchTarget.getaToZFilter());
		assertTextMatches(aToZPage.getResultTextRegex(searchTarget.getKeyword()), aToZPage.getSearchResultSummaryText());
	}

	/**
	 * The the AtoZ Page hover functionality is correct for clickable a-to-z filter labels.
	 */
	@Test
	public void testAtoZHoverClickable() throws Exception {
		AtoZSearchItem availableSearch = aToZPage.getAvailableAtoZSearch();
		System.out.println("keyword: " + availableSearch.getKeyword());

		List<AtoZSearchItem> searchResults = aToZPage.getAtoZSearchResultsForKeyword(availableSearch.getKeyword());
		List<Character> clickableAlphaFilters = aToZPage.getClickableAToZFiltersFromSearchResults(searchResults);

		aToZPage.open();
		aToZPage.enterKeyWordFilter(availableSearch);

		for (char character : clickableAlphaFilters) {
			System.out.println("clickable " + character);

			// Verify the pre hover behaviour.
			WebElement filterLabel = aToZPage.getAToZLabel(character);
			Color preHover = aToZPage.toColor(filterLabel);
			assertThat(String.format("Incorrect color for clickable aToz filter '%s'", character),
					preHover, equalTo(AtoZPage.ATOZ_CLICKABLE_PRE_HOVER_COLOR));

			// Verify the hover behaviour.
			aToZPage.hoverOverAtoZ(character);
			filterLabel = aToZPage.getAToZLabel(character);
			Color hoverColor = aToZPage.toColor(filterLabel);

			assertThat(String.format("Incorrect hover color for clickable aToz filter '%s'", character),
					hoverColor, equalTo(AtoZPage.ATOZ_CLICKABLE_HOVER_COLOR));
		}
	}

	/**
	 * The the AtoZ Page hover functionality is correct for non-clickable a-to-z filter labels.
	 */
	@Test
	public void testAtoZHoverNonClickable() throws Exception {
		AtoZSearchItem availableAtoZSearch = aToZPage.getAvailableAtoZSearch();
		System.out.println("keyword" + availableAtoZSearch.getKeyword());

		List<AtoZSearchItem> searchResults = aToZPage.getAtoZSearchResultsForKeyword(availableAtoZSearch.getKeyword());
		List<Character> nonClickableAlphaFilters = aToZPage.getNonClickableAToZFiltersFromSearchResults(searchResults);

		aToZPage.open();
		aToZPage.enterKeyWordFilter(availableAtoZSearch);

		for (char alpha : nonClickableAlphaFilters) {
			WebElement label = aToZPage.getAToZLabel(alpha);
			WebElement input = aToZPage.getAToZInput(Character.toLowerCase(alpha));

			assertThat(alpha + " should not be clickable.", Boolean.valueOf(input.getAttribute("disabled")), is(true));
			assertThat(alpha + " is not clickable - cursor should not be allowed.", label.getCssValue("cursor"),
					equalTo("not-allowed"));

			Color initialColor = aToZPage.toColor(label);
			assertThat(alpha + " label color incorrect for non clickable item.", initialColor,
					equalTo(AtoZPage.ATOZ_NON_CLICKABLE_COLOR));

			aToZPage.hoverOverElement(label);

			Color hoveringColor = aToZPage.toColor(aToZPage.getAToZLabel(alpha));
			assertThat(alpha + " is non clickable color should not change on hover..", hoveringColor,
					equalTo(AtoZPage.ATOZ_NON_CLICKABLE_COLOR));
		}
	}

}
