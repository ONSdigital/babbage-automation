package com.github.onsdigital.babbage.test.url.redirects;

import au.com.bytecode.opencsv.CSVReader;
import com.github.onsdigital.babbage.test.Configuration;
import com.github.webdriverextensions.Bot;
import com.github.webdriverextensions.WebDriverExtensionsContext;
import com.github.webdriverextensions.internal.junitrunner.DriverPathLoader;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by dave on 1/15/16.
 */
@RunWith(Parameterized.class)
public abstract class AbstractURLRedirectTest {

	protected static final List<String> BROKEN_LINKS = new ArrayList<>();

	@Parameterized.Parameter
	public String currentResourceURL;

	@Parameterized.Parameter(value = 1)
	public String newResourceURL;

	static {
		// invoke the framework method to set the driver paths as expected.
		DriverPathLoader.loadDriverPaths(null);
	}

	@BeforeClass
	public static void setUp() {
		System.out.println("Running URL Redirect tests (Why not get a cup of tea... this could take a while).");

		DesiredCapabilities chromeWindows = new DesiredCapabilities();
		chromeWindows.setCapability("browser", "Chrome");
		chromeWindows.setCapability("browser_version", "31.0");
		chromeWindows.setCapability("os", "Windows");
		chromeWindows.setCapability("os_version", "7");
		chromeWindows.setCapability("browserstack.debug", "true");

		WebDriverExtensionsContext.setDriver(new ChromeDriver(chromeWindows));
		Bot.driver().manage().window().setSize(new Dimension(1600, 1200));
	}

	@AfterClass
	public static void cleanUp() {
		Bot.driver().quit();
		WebDriverExtensionsContext.removeDriver();
	}

	/**
	 *
	 */
	public static Collection<Object[]> getParameters(Reader reader) {
		List<Object[]> result = new ArrayList<>();

		try (
				CSVReader csvReader = new CSVReader(reader)
		) {
			String[] mapping;
			while ((mapping = csvReader.readNext()) != null) {
				//result.add(new Object[] {URLDecoder.decode(mapping[0]), mapping[1]});
				result.add(new Object[] {mapping[0], mapping[1]});
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	@Test
	public void testRedirect() throws Exception {
		String targetUrl = getTargetURL();
		Bot.open(getTargetURL());

		try {
			WebElement modalContinue = Bot.driver().findElement(By.className("btn-modal-continue"));
			modalContinue.click();
			WebDriverWait wait = new WebDriverWait(Bot.driver(), 5);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("btn-modal-continue")));
		} catch (Exception ex) {
			// if modal is not there ignore error and continue.
		}

		URL expected = null;
		URL actual = null;

		try {
			expected = new URL(Configuration.getBabbageUri().toString() + newResourceURL);
			actual = new URL(Bot.driver().getCurrentUrl());
		} catch (MalformedURLException ex) {
			System.out.println("Failed to create URL for " + Bot.driver().getCurrentUrl());
			throw new RuntimeException("Failed to create URL for " + Bot.driver().getCurrentUrl(), ex);
		}

		System.out.println(getRedirectType() + " Redirect: " + targetUrl + " => " + actual.toString());
		String errorMsg = String.format("Incorrect redirect. Expected \n\t'%s' \nfor \n\t'%s'.", expected.toString(), actual.toString());
		verifyRedirect(errorMsg, actual, expected);
	}

	protected void verifyResponseCode(URL actual) {
		try {
			HttpURLConnection connection = (HttpURLConnection)actual.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();

			if (connection.getResponseCode() != Response.Status.OK.getStatusCode()) {
				BROKEN_LINKS.add(currentResourceURL);
			}
			assertThat("Response code incorrect.", connection.getResponseCode(), equalTo(Response.Status.OK.getStatusCode()));
		} catch (IOException e) {
			fail("Whoops");
		}
	}

	@AfterClass
	public static void debug() {
		System.out.println("\n");
		BROKEN_LINKS.stream().forEach(link -> System.out.println(link));
		System.out.println("\n");
	}

	public abstract String getTargetURL() throws MalformedURLException;

	public abstract String getRedirectType();

	public abstract void verifyRedirect(String errorMsg, URL actual, URL expected);
}
