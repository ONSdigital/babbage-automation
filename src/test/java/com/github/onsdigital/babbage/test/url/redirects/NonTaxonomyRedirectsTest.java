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
 * Test the General URL redirects are handled correctly.
 */
@RunWith(Parameterized.class)
public class NonTaxonomyRedirectsTest extends AbstractURLRedirectTest {

	@Parameterized.Parameters
	public static Collection<Object[]> data() {
		Collection<Object[]> result = new ArrayList<>();

		try (
				InputStream in = NonTaxonomyRedirectsTest.class.getResourceAsStream("/url-redirects/general-redirect-mapping.csv");
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
		return Configuration.getBabbageUri().toString() + this.currentResourceURL;
	}

	@Override
	public String getRedirectType() {
		return "Non-Taxonomy";
	}

	@Override
	public void verifyRedirect(String errorMsg, URL actual, URL expected) {
		assertThat(errorMsg, actual.getFile(), equalTo(expected.getFile()));
		assertThat("Host incorrect.", actual.getHost(), equalTo(expected.getHost()));
	}
}
