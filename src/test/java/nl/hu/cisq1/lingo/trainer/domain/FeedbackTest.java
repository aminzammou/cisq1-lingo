package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidFeedbackException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;
import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {

    @ParameterizedTest
    @MethodSource("feedbackMarks")
    void testMarks(String wordToGuess, String attempt, List<Mark> expectedMarks) {
        Feedback feedback = new Feedback(wordToGuess, attempt);
        assertEquals(expectedMarks, feedback.getMarks());
    }

    static Stream<Arguments> feedbackMarks() {
        return Stream.of(
                Arguments.of("BAARD", "BONJE", List.of(CORRECT, ABSENT, ABSENT, ABSENT, ABSENT)),
                Arguments.of("BAARD", "BARST", List.of(CORRECT, CORRECT, PRESENT, ABSENT, ABSENT)),
                Arguments.of("BAARD", "DRAAD", List.of(ABSENT, PRESENT, CORRECT, PRESENT, CORRECT)),
                Arguments.of("BAARD", "BAARD", List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT)),
                Arguments.of("BAROK", "BAARD", List.of(CORRECT, CORRECT, ABSENT, PRESENT, ABSENT)),
                Arguments.of("BAROK", "ZWAAR", List.of(ABSENT, ABSENT, PRESENT, ABSENT, PRESENT)),
                Arguments.of("AARDEN", "APAALM", List.of(CORRECT, ABSENT, PRESENT, ABSENT, ABSENT, ABSENT)),
                Arguments.of("BAROK", "ARARA", List.of(PRESENT, PRESENT, ABSENT, ABSENT, ABSENT))
        );
    }

    @Test
    @DisplayName("word is guessed if all letters are correct")
    void wordIsGuessed() {
        Feedback feedback = new Feedback("woord", "woord");
        assertTrue(feedback.isWordGuessed());
    }

    @Test
    @DisplayName("word is not guessed if not all letters are correct")
    void wordIsNotGuessed() {
        Feedback feedback = new Feedback("woord", "woerd");
        assertFalse(feedback.isWordGuessed());
    }

    @Test
    @DisplayName("word is invalid if all letters are invalid")
    void wordIsInvalid() {
        assertThrows(
                InvalidFeedbackException.class,
                () -> new Feedback("wooorde", "woord")
        );
    }


    @Test
    @DisplayName("feedback is different if values different")
    void feedbackIsDifferent() {
        Feedback feedbackA = new Feedback("ha", "ha");
        Feedback feedbackB = new Feedback("ho", "ha");

        assertNotEquals(feedbackB, feedbackA);
    }

    @Test
    @DisplayName("feedback is same if values same")
    void feedbackIsSame() {
        Feedback feedbackA = new Feedback("ha", "ha");
        Feedback feedbackB = new Feedback("ha", "ha");

        assertEquals(feedbackB, feedbackA);
    }

    @Test
    @DisplayName("hashcade is generated based on values")
    void hashCodeGeneration() {
        Feedback feedbackA = new Feedback("ha", "ha");
        Feedback feedbackB = new Feedback("ha", "ha");

        assertEquals(feedbackB.hashCode(), feedbackA.hashCode());
    }

    @Test
    @DisplayName("toString contains class name")
    void convertedToString() {
        Feedback feedbackA = new Feedback("ha", "ha");
        assertTrue(feedbackA.toString().contains("Feedback"));
    }

    @Test
    @DisplayName("Feedback does not have the same size as the attempt")
    void InvalidLenght() {
        assertThrows(
                InvalidFeedbackException.class,
                () -> new Feedback("kat", "woord")
        );
    }

    @Test
    @DisplayName("Feedback does have the same size as the attempt and there wont be an exeption")
    void validLenght() {
        assertDoesNotThrow(() -> new Feedback("board", "baard"));
    }


    @ParameterizedTest
    @DisplayName("the user guessed some letters of the word")
    @MethodSource("provideHints")
    void giveHint(String wordToGues, String attempt, String oldHint, String expected) {
        Feedback feedback = new Feedback(wordToGues, attempt);
        assertEquals(expected, feedback.getHint(oldHint));
    }

    private static Stream<Arguments> provideHints() {
        return Stream.of(
                Arguments.of("baard", "brood", null, "b...d"),
                Arguments.of("baard", "blaar", "b...d", "b.a.d"),
                Arguments.of("baard", "beren", "b.a.d", "b.a.d"),
                Arguments.of("baard", "baard", "b.a.d", "baard")
        );
    }
}