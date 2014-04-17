package edu.macalester.comp124.hw7;

import org.wikapidia.core.lang.Language;

/**
 * Created by Reena on 4/15/14.
 */
public class NumberOfWords implements Comparable<NumberOfWords> {
    private Language lang;
    private int num;

    public NumberOfWords(Language lang, int num) {
        this.lang = lang;
        this.num = num;
    }

    public Language getLang() { return this.lang; }

    public int getNum() { return this.num; }

    @Override
    public int compareTo(NumberOfWords numberOfWords) {
        return this.getNum() - numberOfWords.getNum();
    }
}
