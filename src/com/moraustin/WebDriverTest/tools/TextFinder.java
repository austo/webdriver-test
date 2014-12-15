package com.moraustin.WebDriverTest.tools;

import com.google.common.base.Joiner;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TextFinder {

    public static class Result {
        public boolean found = false;
        public String text = "";
    }

    private static final String[] DIRECTIVE_START_ENG =
            {"Who", "What", "When", "Where", "Why", "How", "Please", "Is", "Are", "Do", "Can"};

    private static final String START_WORDS = Joiner.on(")|(?:").join(Arrays.asList(DIRECTIVE_START_ENG));

    private static final Pattern START_QUESTION =
            Pattern.compile("^(?:(?:" + START_WORDS + "))(.*?)\\??$", Pattern.CASE_INSENSITIVE);
    private static final Pattern END_QUESTION =
            Pattern.compile("^(.*?)\\?$", Pattern.CASE_INSENSITIVE);

    public static Result isQuestion(String candidate) {
        Result r = new Result();
        Matcher m = START_QUESTION.matcher(candidate);
        if (m.matches()) {
            r.found = true;
            r.text = m.group(1).trim();
            return r;
        }
        m = END_QUESTION.matcher(candidate);
        if (m.matches()) {
            r.found = true;
            r.text = m.group(1).trim();
        }
        return r;
    }
}