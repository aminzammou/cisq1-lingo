package nl.hu.cisq1.lingo.trainer.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.exception.GameHasNotBeenStartedExeption;
import nl.hu.cisq1.lingo.trainer.presentation.dto.AttemptDTO;
import nl.hu.cisq1.lingo.words.data.SpringWordRepository;
import nl.hu.cisq1.lingo.words.domain.Word;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@Import(CiTestConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
class TrainerControllerIntegrationTest {

    @MockBean
    private SpringWordRepository wordRepository;

    @MockBean
    private SpringGameRepository gameRepository;

    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("creating a new game")
    void startNewGame() throws Exception {
        when(wordRepository.findRandomWordByLength(5))
                .thenReturn(Optional.of(new Word("baard")));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/trainer/game");


        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("starting a new round")
    void startNewRound() throws Exception {
        when(wordRepository.findRandomWordByLength(5))
                .thenReturn(Optional.of(new Word("baard")));

        Game game = new Game();

        when(gameRepository.findById(0L))
                .thenReturn(Optional.of(game));


        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/trainer/game/0/round");

        String expectedHint = "b....";

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("PLAYING")))
                .andExpect(jsonPath("$.score", is(0)))
                .andExpect(jsonPath("$.hint", is(expectedHint)))
                .andExpect(jsonPath("$.hint", hasLength(5)))
                .andExpect(jsonPath("$.roundNumber", is(1)));
    }

    @Test
    @DisplayName("making a guess")
    void makingGuess() throws Exception {


        Game game = new Game();
        game.startNewRound("baard");

        when(gameRepository.findById(0L))
                .thenReturn(Optional.of(game));
        AttemptDTO attemptDTO = new AttemptDTO("beard");
        String guessBody = new ObjectMapper().writeValueAsString(attemptDTO);
//        RequestBuilder requestBuilder1 = MockMvcRequestBuilders.post("/trainer/game/0/round");
        RequestBuilder requestBuilder2 = MockMvcRequestBuilders.post("/trainer/game/0/guess").contentType(MediaType.APPLICATION_JSON).content(guessBody);

        String expectedHint = "b.ard";

//        mockMvc.perform(requestBuilder1);
        mockMvc.perform(requestBuilder2)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("PLAYING")))
                .andExpect(jsonPath("$.score", is(25)))
                .andExpect(jsonPath("$.hint", is(expectedHint)))
                .andExpect(jsonPath("$.hint", hasLength(5)));
    }

    @Test
    @DisplayName("trying to make a guess, while round has not been started")
    void roundNotStarted() throws Exception {

        Game game = new Game();

        when(gameRepository.findById(0L))
                .thenReturn(Optional.of(game));
        AttemptDTO attemptDTO = new AttemptDTO("beard");
        String guessBody = new ObjectMapper().writeValueAsString(attemptDTO);

        RequestBuilder requestBuilder2 = MockMvcRequestBuilders.post("/trainer/game/0/guess").contentType(MediaType.APPLICATION_JSON).content(guessBody);

        mockMvc.perform(requestBuilder2)
                .andExpect(jsonPath("$.errorCode").value("CONFLICT"));

    }

    @Test
    @DisplayName("trying to start a new round while, the game does not exist")
    void gameNotFound() throws Exception {
        RequestBuilder requestBuilder1 = MockMvcRequestBuilders.post("/trainer/game/10/round");
        mockMvc.perform(requestBuilder1)
                .andExpect(jsonPath("$.errorCode").value("NOT FOUND"));

    }

    @Test
    @DisplayName("starting a new round, on a game with a still active game going on")
    void alreadyStartANewRound() throws Exception {
        when(wordRepository.findRandomWordByLength(5)).thenReturn(Optional.of(new Word("baard")));
        Game game = new Game();

        when(gameRepository.findById(0L))
                .thenReturn(Optional.of(game));


        RequestBuilder requestBuilder1 = MockMvcRequestBuilders.post("/trainer/game/0/round");
        RequestBuilder requestBuilder2 = MockMvcRequestBuilders.post("/trainer/game/0/round");

        mockMvc.perform(requestBuilder1);
        mockMvc.perform(requestBuilder2)
                .andExpect(jsonPath("$.errorCode").value("CONFLICT"));
    }

    @Test
    @DisplayName("trying to make a guess, while the game has alresy ended")
    void roundAlreadyEnded() throws Exception {


        Game game = new Game();
        game.startNewRound("baard");

        when(gameRepository.findById(0L))
                .thenReturn(Optional.of(game));
        AttemptDTO attemptDTO = new AttemptDTO("baard");
        String guessBody = new ObjectMapper().writeValueAsString(attemptDTO);
        RequestBuilder requestBuilder1 = MockMvcRequestBuilders.post("/trainer/game/0/guess").contentType(MediaType.APPLICATION_JSON).content(guessBody);
        RequestBuilder requestBuilder2 = MockMvcRequestBuilders.post("/trainer/game/0/guess").contentType(MediaType.APPLICATION_JSON).content(guessBody);

        mockMvc.perform(requestBuilder1);
        mockMvc.perform(requestBuilder2)
                .andExpect(jsonPath("$.errorCode").value("CONFLICT"));
    }
}