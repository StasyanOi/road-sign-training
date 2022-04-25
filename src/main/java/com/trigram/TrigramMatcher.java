package com.trigram;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TrigramMatcher {

    public boolean matchTrigrams(List<String> trigrams1, List<String> trigrams2) {
        int firstListSize = trigrams1.size();
        if (firstListSize != trigrams2.size()) {
            throw new IllegalArgumentException("The trigram lists are not equal in size: ");
        }

        var equationList = new ArrayList<Double>(firstListSize);

        for (String trigram : trigrams1) {
            equationList.add(trigrams2.contains(trigram) ? 1.0 : 0);
        }

        double compareResult = equationList.stream().reduce(0d, Double::sum);

        compareResult = compareResult / firstListSize;

        return compareResult > 0.5;
    }
}
