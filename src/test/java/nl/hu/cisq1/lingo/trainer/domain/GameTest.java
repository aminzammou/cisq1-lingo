package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.*;
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
                InvalidWordLength.class,
                () -> game.startNewGame("kat")
        );
    }

    @Test
    @DisplayName("correct amount of letters in the word to guess")
    void wordToGuessValidLenght(){
        assertDoesNotThrow(
                () -> game.startNewGame("katte")
        );
    }

    @Test
    @DisplayName("next amount of letters")
    void WordToGuessIncreased(){
        String fiveLetterWord = "hallo";
        game.startNewGame(fiveLetterWord);
        game.guess(fiveLetterWord);
      assertEquals(game.getWordLength(),6);
    }

    @Test
    @DisplayName("the user cant start a new game when the last round is still playing")
    void RoundIsStillPlaying(){
        String fiveLetterWord = "hallo";
        game.startNewGame(fiveLetterWord);
        assertThrows(
                RoundPlayingExeption.class,
                () ->  game.startNewGame("welkom")
        );
    }

    @Test
    @DisplayName("the user cant guess a word when a game has not been started yet")
    void gameHasNotBeenStartedYed(){
        String fiveLetterWord = "hallo";

        assertThrows(
                GameHasNotBeenStartedExeption.class,
                () ->  game.guess(fiveLetterWord)
        );
    }

    @Test
    @DisplayName("the user cant make a new guess when the round has already ended")
    void RoundHasBeenEnded(){
        String fiveLetterWord = "hallo";
        game.startNewGame(fiveLetterWord);
        game.guess(fiveLetterWord);
        assertThrows(
                GameEndedException.class,
                () ->  game.guess(fiveLetterWord)
        );
    }

    @ParameterizedTest
    @MethodSource("scorePerRound")
    @DisplayName("calculate score based on the amount of attempts in a round")
    void correctScore(int amountOfAttempts, int expectedScore) {
        game.startNewGame("woord");

        for (int i = 0; i < amountOfAttempts - 1; i++) {
            game.guess("words");
        }

        game.guess("woord");

        assertEquals(game.getScore(), expectedScore);
    }


    static Stream<Arguments> scorePerRound() {
        return Stream.of(
                Arguments.of(1, 30),
                Arguments.of(2, 25),
                Arguments.of(3, 20),
                Arguments.of(4, 15),
                Arguments.of(5, 10)
        );
    }

}