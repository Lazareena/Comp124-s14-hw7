package edu.macalester.comp124.hw7;

import org.wikapidia.core.lang.Language;
import org.wikapidia.core.model.LocalPage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static edu.macalester.comp124.hw7.Utils.*;
import edu.macalester.comp124.hw7.Utils.*;

/**
 * The language detector should detect the language of a text.
 * It must be "trained" to learn the words that appear in each language.
 * After it is trained, detect will be called for each text.
 *
 * @author Shilad Sen
 */
public class LanguageDetector {
    /**
     * Wrapper over the WikAPIdia API.
     */
    private final WikAPIdiaWrapper wrapper;


    // Add your instance variables here.
    HashMap<String,Integer> welsh = new HashMap<String, Integer>();
    HashMap<String,Integer> simple = new HashMap<String, Integer>();
    HashMap<String,Integer> hindi = new HashMap<String, Integer>();
    HashMap<String,Integer> bosnian = new HashMap<String, Integer>();
    HashMap<String,Integer> icelandic = new HashMap<String, Integer>();
    HashMap<String,Integer> scots = new HashMap<String, Integer>();
    /**
     * Constructs a new language detector.
     * @param wrapper
     */
    public LanguageDetector(WikAPIdiaWrapper wrapper) {
        this.wrapper = wrapper;
    }

    /**
     * Learn the words used in each language.
     * Only needs to be called once each time your program is run.
     */
    public void train() {
        for (int i = 0; i < ALL_LANGS.size(); i++) {
            List<String> allTextForEachLang = wrapper.getPageTexts(ALL_LANGS.get(i), 1000);

            for (String text : allTextForEachLang) {
                List<String> words = Utils.splitWords(text);

                for (String word : words) {
                    if (ALL_LANGS.get(i) == LANG_WELSH) {
                        if (welsh.containsKey(word)) {
                            int newValue = welsh.get(word) + 1;
                            welsh.put(word, newValue);
                        } else {
                            welsh.put(word, 1);
                        }
                    } else if (ALL_LANGS.get(i) == LANG_SIMPLE) {
                        if (simple.containsKey(word)) {
                            int newValue = simple.get(word) + 1;
                            simple.put(word, newValue);
                        } else {
                            simple.put(word, 1);
                        }
                    } else if (ALL_LANGS.get(i) == LANG_HINDI) {
                        if (hindi.containsKey(word)) {
                            int newValue = hindi.get(word) + 1;
                            hindi.put(word, newValue);
                        } else {
                            hindi.put(word, 1);
                        }
                    } else if (ALL_LANGS.get(i) == LANG_BOSNIAN) {
                        if (bosnian.containsKey(word)) {
                            int newValue = bosnian.get(word) + 1;
                            bosnian.put(word, newValue);
                        } else {
                            bosnian.put(word, 1);
                        }
                    } else if (ALL_LANGS.get(i) == LANG_ICELANDIC) {
                        if (icelandic.containsKey(word)) {
                            int newValue = icelandic.get(word) + 1;
                            icelandic.put(word, newValue);
                        } else {
                            icelandic.put(word, 1);
                        }
                    } else if (ALL_LANGS.get(i) == LANG_SCOTS) {
                        if (scots.containsKey(word)) {
                            int newValue = scots.get(word) + 1;
                            scots.put(word, newValue);
                        } else {
                            scots.put(word, 1);
                        }
                    }
                }
            }
        }
    }

    /**
     * Detect the language associated with a text.
     * @param text
     * @return
     */
    public Language detect(String text) {
       List<String> listOfWords = Utils.splitWords(text);
       List<NumberOfWords> numberOfTimesInEachLanguage = new ArrayList<NumberOfWords>();
       train();

        int numberOfTimesInWelsh= 0;
        int numberOfTimesInHindi= 0;
        int numberOfTimesInBosnian= 0;
        int numberOfTimesInSimple= 0;
        int numberOfTimesInIcelandic= 0;
        int numberOfTimesInScots= 0;

        for(String word: listOfWords) {
            if (welsh.containsKey(word)) {
            numberOfTimesInWelsh+=welsh.get(word);
            }
            if (hindi.containsKey(word)) {
            numberOfTimesInHindi+=hindi.get(word);
            }
            if (bosnian.containsKey(word)) {
            numberOfTimesInBosnian+=bosnian.get(word);
            }
            if (simple.containsKey(word)) {
            numberOfTimesInSimple+=simple.get(word);
            }
            if (icelandic.containsKey(word)) {
            numberOfTimesInIcelandic+=icelandic.get(word);
            }
            if (scots.containsKey(word)) {
            numberOfTimesInScots+=scots.get(word);
            }
        }

        numberOfTimesInEachLanguage.add(new NumberOfWords(LANG_WELSH, numberOfTimesInWelsh));
        numberOfTimesInEachLanguage.add(new NumberOfWords(LANG_HINDI, numberOfTimesInHindi));
        numberOfTimesInEachLanguage.add(new NumberOfWords(LANG_BOSNIAN, numberOfTimesInBosnian));
        numberOfTimesInEachLanguage.add(new NumberOfWords(LANG_SIMPLE, numberOfTimesInSimple));
        numberOfTimesInEachLanguage.add(new NumberOfWords(LANG_ICELANDIC, numberOfTimesInIcelandic));
        numberOfTimesInEachLanguage.add(new NumberOfWords(LANG_SCOTS, numberOfTimesInScots));

        Collections.sort(numberOfTimesInEachLanguage);

        return numberOfTimesInEachLanguage.get(numberOfTimesInEachLanguage.size()-1).getLang();

    }

    public static void main(String args[]) throws IOException {
        // Test to make sure the database is installed properly
        WikAPIdiaWrapper wrapper = new WikAPIdiaWrapper();
        LocalPage page = wrapper.getLocalPageByTitle(LANG_SIMPLE, "Apple");
        System.out.println("Apple in other languages:");
        for (LocalPage page2 : wrapper.getInOtherLanguages(page)) {
            System.out.println("\t" + page2.getLanguage() + ": " + page2.getTitle());
        }

        // prepare the detector
        LanguageDetector detector = new LanguageDetector(wrapper);
        detector.train();

        // use the detector
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in, "utf-8"));
        while (true) {
            System.out.println("Enter text to detect language, or 'stop'.");
            String text = in.readLine();
            if (text.trim().equalsIgnoreCase("stop")) {
                break;
            }
            System.out.println("language of text is " + detector.detect(text));
        }
    }
}
