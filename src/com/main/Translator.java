package com.main;

import com.lang.Language;
import com.lang.LanguageDetector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Translator {

    @Autowired
	private Dictionary dictionary;
    @Autowired
    private LanguageDetector languageDetector;
    @Autowired
    private TextSource textSource;

	/**
	 * Translates content of specified file
	 * @param source path to file
	 * @return translated file
	 */
    public String translate(String source) {
        String text = textSource.getText(source);
        Language language = languageDetector.detectLanguage(text);
        StringBuilder translation = new StringBuilder();
        for (String word : text.split("\\s+")) {
            translation.append(dictionary.translate(word, language));
        }
		return translation.toString();
	}

}
