package com.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.StringJoiner;

@Component
public class TextSource {

	@Autowired
	private ResourceLoader resourceLoader;

    /**
     * Returns text from source file
     * @param path path to file
     * @return String which contains specified file content
     */
	public String getText(String path) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        List<String> wordsList = resourceLoader.load(path);
        for (String word : wordsList) {
            stringJoiner.add(word);
        }
        return stringJoiner.toString();
	}

}
