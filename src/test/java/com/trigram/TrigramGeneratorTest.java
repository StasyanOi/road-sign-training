package com.trigram;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrigramGeneratorTest {

    final TrigramGenerator trigramGenerator = new TrigramGenerator();

    @Test
    void generateTrigramsSmallStringTest() {
        var trigrams = trigramGenerator.generateTrigrams("string");
        assertEquals(List.of(" st", "str", "tri", "rin", "ing", "ng "), trigrams);
    }

    @Test
    void generateTrigramsBigStringTest() {
        var trigrams = trigramGenerator.generateTrigrams("thebesthingever");
        assertEquals(List.of(" th", "the", "heb", "ebe", "bes", "est", "sth",
                "thi", "hin","ing","nge","gev", "eve","ver", "er "), trigrams);
    }
}