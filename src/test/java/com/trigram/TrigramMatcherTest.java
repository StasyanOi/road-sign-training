package com.trigram;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrigramMatcherTest {

    TrigramMatcher trigramMatcher = new TrigramMatcher();

    @Test
    void matchTrigramsTrueTest() {
        List<String> trigrams1 = List.of("qwe", "rty");
        List<String> trigrams2 = List.of("qwe", "rty");
        boolean hasEqualTrigrams = trigramMatcher.matchTrigrams(trigrams1, trigrams2);
        assertTrue(hasEqualTrigrams);
    }

    @Test
    void matchTrigramsFalseTest() {
        List<String> trigrams1 = List.of("qwe", "rty");
        List<String> trigrams2 = List.of("asd", "fgh");
        boolean hasEqualTrigrams = trigramMatcher.matchTrigrams(trigrams1, trigrams2);
        assertFalse(hasEqualTrigrams);
    }
}