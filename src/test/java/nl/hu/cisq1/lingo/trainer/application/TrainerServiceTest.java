package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.GameState;
import nl.hu.cisq1.lingo.trainer.domain.Progress;
import nl.hu.cisq1.lingo.trainer.domain.exception.GameNotFoundException;
import nl.hu.cisq1.lingo.trainer.domain.exception.LostGameException;
import nl.hu.cisq1.lingo.trainer.domain.exception.RoundHasNotBeenStartedException;
import nl.hu.cisq1.lingo.trainer.domain.exception.RoundPlayingException;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TrainerServiceTest {
    WordService wordService;
    SpringGameRepository gameRepository;
    TrainerService service;


    @BeforeEach
    void before() {
        Game game = new Game();
        wordService = mock(WordService.class);

        when(wordService.provideRandomWord(5)).thenReturn("baard");
        when(wordService.provideRandomWord(6)).thenReturn("baarde");
        when(wordService.provideRandomWord(7)).thenReturn("baarden");
        gameRepository = mock(SpringGameRepository.class);
        when(gameRepository.findById(0L)).thenReturn(java.util.Optional.of(game));

        service = new TrainerService(wordService, gameRepository);
    }

    @Test
    @DisplayName("first round has been won")
    void wonRound() throws GameNotFoundException, LostGameException {
        service.startNewRound(0L);
        Progress progress = service.guess(0L, "baard");
        assertEquals(progress.getStatus(), GameState.WON);
    }

    @Test
    @DisplayName("Game has not been found exception")
    void unsupportedLength() {
        assertThrows(GameNotFoundException.class, () -> service.startNewRound(2L));
        assertThrows(GameNotFoundException.class, () -> service.guess(2L, " "));
    }

    @Test
    @DisplayName("Game has not been started yet")
    void gameHasNotBeenStartedYet() {
        assertThrows(RoundHasNotBeenStartedException.class, () -> service.guess(0L, " "));
    }

    @Test
    @DisplayName("Game has already been started")
    void gameHasAlreadyStarted() throws GameNotFoundException, LostGameException {
        service.startNewRound(0L);
        assertThrows(RoundPlayingException.class, () -> service.startNewRound(0L));
    }

    @Test
    @DisplayName("Game has been started")
    void gameHasStarted() {
        assertDoesNotThrow(() -> service.startNewRound(0L));
    }

    @Test
    @DisplayName("User tries to make a guess when a round has already been lost")
    void roundHasAlreadyBeenEnded() throws GameNotFoundException, LostGameException {
        service.startNewRound(0L);
        for (int i = 0; i < 6 - 1; i++) {
            service.guess(0l, "board");
        }
        assertThrows(LostGameException.class, () -> service.guess(0l, "board"));
    }

    @Test
    @DisplayName("User tries to start a new round when a game has already been lost")
    void gameHasAlreadyBeenEnded() throws GameNotFoundException, LostGameException {
        service.startNewRound(0L);
        for (int i = 0; i < 6 - 1; i++) {
            service.guess(0l, "board");
        }
        assertThrows(LostGameException.class, () -> service.startNewRound(0l));
    }

}