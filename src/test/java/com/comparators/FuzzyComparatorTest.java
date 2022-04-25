package com.comparators;

import com.trigram.TrigramGenerator;
import com.trigram.TrigramMatcher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.internal.verification.Calls;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FuzzyComparatorTest {

    @InjectMocks
    FuzzyComparator fuzzyComparator;

    @Mock
    TrigramMatcher trigramMatcher;

    @Mock
    TrigramGenerator trigramGenerator;

    @Test
    void compareFailTest() {
        var firstPhrase = "First phrase";
        var secondPhrase = "";

        when(trigramGenerator.generateTrigrams("Firstphrase"))
                .thenReturn(List.of("1", "2", "3"));
        when(trigramGenerator.generateTrigrams("           "))
                .thenReturn(List.of("4", "5", "6"));

        when(trigramMatcher.matchTrigrams(List.of("1", "2", "3"), List.of("4", "5", "6")))
                .thenReturn(false);

        boolean isEqual = fuzzyComparator.compare(firstPhrase, secondPhrase);

        assertFalse(isEqual);
        verify(trigramGenerator).generateTrigrams("Firstphrase");
        verify(trigramGenerator).generateTrigrams("           ");
        verify(trigramMatcher).matchTrigrams(List.of("1", "2", "3"), List.of("4", "5", "6"));
        verifyNoMoreInteractions(trigramMatcher, trigramGenerator);
    }

    @Test
    void compareSuccessTest() {
        var firstPhrase = "phrase";
        var secondPhrase = "phrase";

        when(trigramGenerator.generateTrigrams("phrase"))
                .thenReturn(List.of("1", "2", "3"));

        when(trigramMatcher.matchTrigrams(List.of("1", "2", "3"), List.of("1", "2", "3")))
                .thenReturn(true);

        boolean isEqual = fuzzyComparator.compare(firstPhrase, secondPhrase);

        assertTrue(isEqual);
        verify(trigramGenerator, VerificationModeFactory.times(2))
                .generateTrigrams("phrase");
        verify(trigramMatcher).matchTrigrams(List.of("1", "2", "3"), List.of("1", "2", "3"));
        verifyNoMoreInteractions(trigramMatcher, trigramGenerator);
    }
}