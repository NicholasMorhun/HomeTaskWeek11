package com.lang;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LanguageDetector {

	private Map<Language, List<String>> mapping;

	public Language detectLanguage(String text) {
		if (mapping == null) {
			initMapping();
		}
		String firstWord = text.split(" ", 2)[0];
		for (Language language : mapping.keySet()) {
			if (mapping.get(language).contains(firstWord)) {
                return language;
            }
		}
        throw new RuntimeException("Cannot detect language");
	}

	private void initMapping() {
		mapping = new HashMap<Language, List<String>>();
	}

}
