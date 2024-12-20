package io.trigram;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TrigramGenerator {

    public List<String> generateTrigrams(String str) {
        var stringBuilder = new StringBuilder();
        stringBuilder.append(" ").append(str).append(" ");
        List<String> trigrams = new ArrayList<>();

        int startTrigram = 0;
        int endTrigram = 3;

        while (endTrigram < stringBuilder.length() + 1) {
            trigrams.add(stringBuilder.substring(startTrigram, endTrigram));
            startTrigram++;
            endTrigram++;
        }
        return trigrams;
    }
}
