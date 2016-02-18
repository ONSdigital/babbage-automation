package com.github.onsdigital.babbage.test.url.redirects;

import com.github.onsdigital.babbage.test.Configuration;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 */
@RunWith(Parameterized.class)
public class TaxonomyRedirectsTest extends AbstractURLRedirectTest {

	public static final String TAXONOMY_URI = "/ons/taxonomy/index.html?nscl=";

	@Parameterized.Parameters
	public static Collection<Object[]> data() {
		Collection<Object[]> result = new ArrayList<>();

		try (
				InputStream in = TaxonomyRedirectsTest.class.getResourceAsStream("/url-redirects/taxonomy-redirect-mapping.csv");
				BufferedReader fileReader = new BufferedReader(new InputStreamReader(in))
		) {
			result =  getParameters(fileReader);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	@Override
	public String getTargetURL() throws MalformedURLException {
		return Configuration.getBabbageUri().toString() + TAXONOMY_URI + currentResourceURL;
	}

	@Override
	public String getRedirectType() {
		return "Taxonomy";
	}

	public void verifyRedirect(String errorMsg, URL actual, URL expected) {
		assertThat(errorMsg, actual.getPath(), equalTo(expected.getPath()));

		// Ignore host for this specific path - redirecting to beta.
		if (!expected.getPath().startsWith("/economy/inflationandpriceindices")) {
			assertThat("Host is in correct.", actual.getHost(), equalTo(expected.getHost()));
		}
	}
}
