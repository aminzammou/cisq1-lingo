package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
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
        // Test game
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
//        service.startNewGame();
    }


    @Test
    @DisplayName("Game has not been found exception")
    void unsupportedLength() {
        assertThrows(RuntimeException.class, () -> service.startNewRound(2L));
        assertThrows(RuntimeException.class, () -> service.guess(2L," "));
    }
}