package edu.macalester.comp124.hw7;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.wikapidia.core.lang.Language;
import org.wikapidia.core.model.LocalPage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * A program to extract entities from a possibly multilingual text.
 *
 * @author Shilad Sen
 */
public class EntityExtractor {
    private final WikAPIdiaWrapper wrapper;
    private final LanguageDetector detector;

    /**
     * Creates a new entity extractor.
     * @param wrapper
     * @param detector
     */
    public EntityExtractor(WikAPIdiaWrapper wrapper, LanguageDetector detector) {
        this.wrapper = wrapper;
        this.detector = detector;
    }

    /**
     * Extracts entities from a text in some unknown language and prints them using System.out.
     * @param text The text to extract entities from
     * @param goal The target language to translate entities to.
     */
    public void extract(String text, Language goal) {
    }

    public static void main(String args[]) throws IOException {
    }
}
