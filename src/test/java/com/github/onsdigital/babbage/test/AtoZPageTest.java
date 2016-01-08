package com.github.onsdigital.babbage.test;

import com.github.onsdigital.babbage.test.base.BrowserTestBase;
import com.github.onsdigital.babbage.test.page.AtoZPage;
import com.github.webdriverextensions.Bot;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.awt.*;

import static com.github.webdriverextensions.Bot.assertTextMatches;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

/**
 * Created by dave on 1/7/16.
 */
public class AtoZPageTest extends BrowserTestBase {

	private String STATS_BULLITINS_REGEX = "[0-9]+ statistical bulletins( containing \'%s\'){0,1}";
	private String STATS_BULLITINS_WITH_FILTER_REGEX = "[0-9]+ statistical bulletins containing \'%s\' that begin with the letter \'%s\'";

	private static final String URI = "atoz";

	private AtoZPage aToZPage = new AtoZPage(URI);

	// TODO - this test requires an update to handlebars in babbage - fix single quotes.
	@Ignore
	@Test
	public void testGoToAtoZ() throws Exception {
		String keyWord = "public";
		aToZPage.open();

		String regex = String.format(STATS_BULLITINS_REGEX, keyWord.toLowerCase());
		assertTextMatches(regex,  aToZPage.getSearchResultSummaryText());

		aToZPage.enterKeyWordFilter(keyWord);

		aToZPage.useAtoZFilter('C');
		regex = String.format(STATS_BULLITINS_WITH_FILTER_REGEX, keyWord, "c");

		assertTextMatches(regex,  aToZPage.getSearchResultSummaryText());
	}

	@Test
	public void testAtoZHover() throws Exception {
		final char target = 'D';
		aToZPage.open();

		WebElement hoverItem = aToZPage.getAToZItem(target);
		Color preHover = aToZPage.toColor(hoverItem.getCssValue("background"));
		aToZPage.hoverOverAtoZ(target);

		Color hovering = aToZPage.toColor(aToZPage.getAToZItem(target).getCssValue("background"));

		Bot.assertThat("A to Z item did not change color while being hovered over.", preHover, is(not(hovering)));
	}

}
