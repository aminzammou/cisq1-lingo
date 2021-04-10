package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.trainer.domain.GameState;
import nl.hu.cisq1.lingo.trainer.domain.Progress;
import nl.hu.cisq1.lingo.trainer.domain.exception.GameNotFoundException;
import nl.hu.cisq1.lingo.trainer.domain.exception.LostGameException;
import nl.hu.cisq1.lingo.words.data.SpringWordRepository;
import nl.hu.cisq1.lingo.words.domain.Word;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@Import(CiTestConfiguration.class)
@Transactional
public class TrainerServiceIntegrationTest {
    @Autowired
    private TrainerService trainerService;

    @MockBean
    private SpringWordRepository repository;

    private Long id;

    @BeforeEach
    void setup() throws GameNotFoundException, LostGameException {
        when(repository.findRandomWordByLength(5)).thenReturn(Optional.of(new Word("baard")));
        id = trainerService.startNewGame();
        trainerService.startNewRound(id);
    }

    @Test
    @DisplayName("Starting a new round")
    void startNewRound() throws LostGameException, GameNotFoundException {
        Long id = trainerService.startNewGame();
        Progress progress = trainerService.startNewRound(id);

        assertEquals(GameState.PLAYING,progress.getStatus());
        assertEquals(0,progress.getScore());
        assertEquals(5,progress.getHint().length());
    }

    @Test
    @DisplayName("winning a new round")
    void winningAnRound() throws LostGameException, GameNotFoundException {

        Progress progress = trainerService.guess(id,"baard");

        assertEquals(GameState.WON,progress.getStatus());
        assertEquals(25,progress.getScore());
    }

    @Test
    @DisplayName("losing a new round")
    void losingAnRound() throws LostGameException, GameNotFoundException {


        Progress progress = null;
        for(int i = 0; i < 5; i++) {
            progress = trainerService.guess(id,"board");
        }
        assertEquals(GameState.ELIMINATED,progress.getStatus());
        assertEquals(5,progress.getScore());
    }
}
