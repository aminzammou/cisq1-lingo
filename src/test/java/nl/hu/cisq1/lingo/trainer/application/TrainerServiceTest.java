package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.exception.*;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TrainerServiceTest {
    WordService wordService;
    SpringGameRepository gameRepository;
    TrainerService service;


    @BeforeEach
    void before() {
        Game game = new Game();
        wordService = mock(WordService.class);

        // Mocks
        when(wordService.provideRandomWord(5)).thenReturn("baard");
        when(wordService.provideRandomWord(6)).thenReturn("baarde");
        when(wordService.provideRandomWord(7)).thenReturn("baarden");
        gameRepository = mock(SpringGameRepository.class);
        when(gameRepository.findById(0L)).thenReturn(java.util.Optional.of(game));

        // Trainer
        service = new TrainerService(wordService, gameRepository);
    }


    @Test
    @DisplayName("Game has not been found exception")
    void unsupportedLength() {
        assertThrows(RuntimeException.class, () -> service.startNewRound(2L));
        assertThrows(RuntimeException.class, () -> service.guess(2L," "));
    }

    @Test
    @DisplayName("Game has not been started yet")
    void gameHasNotBeenStartedYet() {
        assertThrows(GameHasNotBeenStartedExeption.class, () -> service.guess(0L," "));
    }

    @Test
    @DisplayName("Game has already been started")
    void gameHasAlreayStarted() throws GameNotFoundException, LostGameException {
        service.startNewRound(0L);
        assertThrows(RoundPlayingExeption.class, () -> service.startNewRound(0L));
    }

    @Test
    @DisplayName("Game has already been ended")
    void gameHasAlreayBeenEnded() throws GameNotFoundException, LostGameException {
        service.startNewRound(0L);
        for (int i = 0; i < 6 - 1; i++) {
            service.guess(0l,"board");
        }
        assertThrows(GameEndedException.class, () -> service.guess(0l,"board"));
    }


}