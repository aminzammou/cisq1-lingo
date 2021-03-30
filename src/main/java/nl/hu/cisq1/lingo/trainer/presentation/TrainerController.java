package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import nl.hu.cisq1.lingo.trainer.domain.Progress;
import nl.hu.cisq1.lingo.trainer.domain.exception.GameNotFoundException;
import nl.hu.cisq1.lingo.trainer.domain.exception.LostGameException;
import nl.hu.cisq1.lingo.trainer.domain.exception.RoundHasNotBeenStartedExeption;
import nl.hu.cisq1.lingo.trainer.domain.exception.RoundPlayingExeption;
import nl.hu.cisq1.lingo.trainer.presentation.dto.AttemptDTO;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/trainer")
public class TrainerController {
    private final TrainerService service;

    public TrainerController(TrainerService service) {
        this.service = service;
    }

    @PostMapping("/game")
    public Long createNewGame() {
       return this.service.startNewGame();
    }

    @PostMapping("/game/{id}")
    public Progress createNewRound(@PathVariable Long id) throws GameNotFoundException, LostGameException, RoundPlayingExeption {
        return service.startNewRound(id);
    }

    @PostMapping("/game/{id}/guess")
    public Progress guess(@PathVariable Long id, @RequestBody AttemptDTO attemptDTO) throws GameNotFoundException, LostGameException, RoundHasNotBeenStartedExeption {
        return service.guess(id,attemptDTO.getAttempt());
    }
}
