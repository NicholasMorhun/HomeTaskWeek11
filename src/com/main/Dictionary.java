package com.main;

import com.lang.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Dictionary {

    private static final Pattern dictLine = Pattern.compile("\\s*([A-Za-zА-Яа-я])\\s+=\\s+([A-Za-zА-Яа-я])\\s+");

    @Autowired
    private ResourceLoader resourceLoader;

    private Map<Language, Map<String, String>> dictionaries = new HashMap<Language, Map<String, String>>();

	public String translate(String word, Language language) {
        String translation = getDictionary(language).get(word);
		return (translation != null) ? translation : word;
	}

	private Map<String, String> getDictionary(Language language) {
		Map<String, String> dictionary = dictionaries.get(language);
		if (null == dictionary) {
			dictionary = loadDictionary(language);
			dictionaries.put(language, dictionary);
		}
		return dictionary;
	}

	private Map<String, String> loadDictionary(Language language) {
        Path path;
        switch (language) {
            case ENGLISH:
                path = Paths.get("dict/english.dict");
                break;
            case RUSSIAN:
                path = Paths.get("dict/russian.dict");
                break;
            default:
                throw new RuntimeException("Unknown language");
        }
        return loadDictionaryFromFile(path);
	}

    private Map<String, String> loadDictionaryFromFile(Path path) {
        List<String> dictionaryLines = resourceLoader.load(path.toString());
        Map<String, String> dictionary = new HashMap<String, String>();
        Matcher matcher;
        for (String line : dictionaryLines) {
            matcher = dictLine.matcher(line);
            String word = matcher.group(1);
            String translation = matcher.group(2);
            dictionary.put(word, translation);
        }
        return dictionary;
    }

}
