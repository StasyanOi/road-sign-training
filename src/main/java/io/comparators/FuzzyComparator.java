package io.comparators;

import io.trigram.TrigramGenerator;
import io.trigram.TrigramMatcher;
import org.springframework.stereotype.Component;

@Component
public class FuzzyComparator {

    private final TrigramGenerator trigramGenerator;
    private final TrigramMatcher trigramMatcher;

    public FuzzyComparator(TrigramGenerator trigramGenerator, TrigramMatcher trigramMatcher) {
        this.trigramGenerator = trigramGenerator;
        this.trigramMatcher = trigramMatcher;
    }

    public boolean compare(String string1, String string2) {

        string1 = string1.trim().replace(" ", "");
        string2 = string2.trim().replace(" ", "");

        if (string1.length() != string2.length()) {
            int absLengthDifference = Math.abs(string1.length() - string2.length());
            if (string1.length() > string2.length()) {
                string2 = string2 + " ".repeat(absLengthDifference);
            } else {
                string1 = string1 + " ".repeat(absLengthDifference);
            }
        }


        return trigramMatcher.matchTrigrams(trigramGenerator.generateTrigrams(string1),
                trigramGenerator.generateTrigrams(string2));
    }
}
