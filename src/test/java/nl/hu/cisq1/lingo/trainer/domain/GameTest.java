package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidFeedbackException;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidWordLenght;
import nl.hu.cisq1.lingo.trainer.domain.exception.RoundPlayingExeption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Game game;

    @BeforeEach
    void createGame(){
        game = new Game();
    }

    @Test
    @DisplayName("incorrect amount of letters in the word to guess")
    void wordToGuessInvalidLenght(){
        assertThrows(
                InvalidWordLenght.class,
                () -> game.StartNewGame("kat")
        );
    }

    @Test
    @DisplayName("correct amount of letters in the word to guess")
    void wordToGuessValidLenght(){
        assertDoesNotThrow(
                () -> game.StartNewGame("katte")
        );
    }

    @Test
    @DisplayName("next amount of letters")
    void WordToGuessIncreased(){
        String fiveLetterWord = "hallo";
        game.StartNewGame(fiveLetterWord);
        game.guess(fiveLetterWord);
      assertEquals(game.getWordLenght(),6);
    }

    @Test
    @DisplayName("the user cant start a new game when the last round is still playing")
    void RoundIsStillPlaying(){
        String fiveLetterWord = "hallo";
        game.StartNewGame(fiveLetterWord);
        assertThrows(
                RoundPlayingExeption.class,
                () ->  game.StartNewGame("welkom")
        );
    }

    //Test score
//    @ParameterizedTest
//    @MethodSource("score")
//    @DisplayName("score increases")
//    void correctScore(String WordToGuess, int expectedScore) {
//        game.StartNewGame(WordToGuess);
//        game.guess(WordToGuess);
//
//        assertEquals(game.getScore(), expectedScore);
//
//    }
//
//
//    static Stream<Arguments> score() {
//        return Stream.of(
//                Arguments.of("baard",125),
//                Arguments.of("baarde",50),
//                Arguments.of("baarden",75)
//        );
//    }

}