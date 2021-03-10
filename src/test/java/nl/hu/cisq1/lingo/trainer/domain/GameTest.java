package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidFeedbackException;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidWordLenght;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

}