package com.github.onsdigital.babbage.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by dave on 1/11/16.
 */
public class AtoZSearchItem {

	static final Pattern ANY_CHAR_REGEX = Pattern.compile("^[a-zA-Z]$");
	static final Pattern WORDS_ONLY_REGEX = Pattern.compile("^[a-zA-Z]+$");

	private String title;
	private String summary;
	private String keyword;
	private char aToZFilter;

	private AtoZSearchItem(String title, String summary, List<String> keywords) {
		this.title = title;
		this.summary = summary;
		this.keyword = keywords.get(new Random().nextInt(keywords.size()));

		for (String word : title.split(" ")) {
			String firstLetter = String.valueOf(word.charAt(0));
			if (ANY_CHAR_REGEX.matcher(firstLetter).matches()) {
				this.aToZFilter = firstLetter.charAt(0);
				break;
			}
		}
	}

	public String getTitle() {
		return title;
	}

	public String getSummary() {
		return summary;
	}

	public String getKeyword() {
		return this.keyword;
	}

	public char getaToZFilter() {
		return aToZFilter;
	}

	public static class Builder {
		String title;
		String summary;
		Set<String> keywords;

		public Builder title(String title) {
			this.title = title;
			return this;
		}

		public Builder summary(String summary) {
			this.summary = summary;
			return this;
		}

		public Builder keywords(String input) {
			input = input.toLowerCase().trim();

			if (input.startsWith("keywords:")) {
				input = input.substring(input.indexOf(":") + 1);
			}
			String[] keyPhrases = input.split(",");

			keywords = new HashSet<>();
			for (String phrase : keyPhrases) {
				String[] words = phrase.split(" ");

				for (String word : words) {
					word = word.trim();
					if (WORDS_ONLY_REGEX.matcher(word).matches()) {
						keywords.add(word);
					}
				}
			}
			return this;
		}

		public AtoZSearchItem build() {
			return new AtoZSearchItem(this.title, this.summary, new ArrayList<>(keywords));
		}
	}
}
