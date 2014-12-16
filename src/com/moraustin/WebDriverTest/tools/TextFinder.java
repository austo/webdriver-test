package com.moraustin.WebDriverTest.tools;

import com.google.common.base.Joiner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TextFinder {

    public static class Result {
        public boolean found = false;
        public String text = "";
        public List<String> subjects = new ArrayList<>();
    }

    // TODO: map of root to synonym
    private static final String[] DIRECTIVE_START_ENG = {
            "Who", "What", "When", "Where", "Why", "How", "Please", "Is", "Are", "Do", "Can"
    };
    private static final String[] SCREENER_QUESTION_SUBJECTS = {
            "age", "income", "sex", "gender", "occupation", "employer", "ethnic"
    };

    private static final List<Pattern> SCREENER_QUESTION_PATTERNS = initScreenerQuestions();

    private static List<Pattern> initScreenerQuestions() {
        List<Pattern> patterns = new ArrayList<>();
        Arrays.asList(SCREENER_QUESTION_SUBJECTS).forEach((String s) -> {
            patterns.add(Pattern.compile(s, Pattern.CASE_INSENSITIVE));
        });
        return patterns;
    }

    private static final String START_WORDS = Joiner.on(")|(?:").join(Arrays.asList(DIRECTIVE_START_ENG));

    private static final Pattern START_QUESTION =
            Pattern.compile("^(?:(?:" + START_WORDS + "))(.*?)\\??$", Pattern.CASE_INSENSITIVE);
    private static final Pattern END_QUESTION =
            Pattern.compile("^(.*?)\\?$", Pattern.CASE_INSENSITIVE);

    public static Result isQuestion(String candidate) {
        Result r = new Result();
        for (Pattern p : new Pattern[]{START_QUESTION, END_QUESTION}) {
            Matcher m = p.matcher(candidate);
            if (m.matches()) {
                r.found = true;
                r.text = m.group(1).trim();
                for (Pattern sp : SCREENER_QUESTION_PATTERNS) {
                    Matcher sm = sp.matcher(r.text);
                    if (sm.find()) {
                        r.subjects.add(sp.pattern());
                    }
                }
                return r;
            }
        }
        return r;
    }
}