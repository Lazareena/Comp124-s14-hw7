package edu.macalester.comp124.hw7;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.wikapidia.core.lang.Language;
import org.wikapidia.core.model.LocalPage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static edu.macalester.comp124.hw7.Utils.*;
import edu.macalester.comp124.hw7.Utils.*;
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
     * Extracts entities from a text in some unknown language and prints them using System.out.pri
     * @param text The text to extract entities from
     * @param goal The target language to translate entities to.
     */
    public void extract(String text, Language goal) {
        Language langOfText = detector.detect(text);
        System.out.println("Translating text from" + " " + langOfText + " "+ "to" + " " + goal + " " + "found entities:");
        List<String> words = Utils.splitWords(text);

        for (int i = 0; i < words.size(); i++) {
            if (wrapper.getLocalPageByTitle(langOfText, words.get(i)) != null) {
                List<LocalPage> otherLang = wrapper.getInOtherLanguages(wrapper.getLocalPageByTitle(langOfText, words.get(i)));
                for (LocalPage lp : otherLang) {
                    if (lp.getLanguage() == goal) {
                        System.out.println("\t" + "'" + words.get(i) + "'" + " "+"=>" + " " +lp.getTitle());
                        break;
                    }
                }
            } else {
                System.out.println("\t" + "'" + words.get(i) + "'" + " "+ "=>" + " " + "unknown");
            }
        }
    }

    public static void main(String args[]) throws IOException {
        WikAPIdiaWrapper wrapper = new WikAPIdiaWrapper();
        LanguageDetector detector = new LanguageDetector(wrapper);

        EntityExtractor entityExtractor = new EntityExtractor(wrapper, detector);

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in, "utf-8"));
        while (true) {
            System.out.println("Enter text, or 'stop'.");
            String text = in.readLine();
            if (text.trim().equalsIgnoreCase("stop")) {
                break;
            }
            entityExtractor.extract(text, LANG_SIMPLE);
        }

    }
}
