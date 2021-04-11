package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import nl.hu.cisq1.lingo.trainer.domain.Progress;
import nl.hu.cisq1.lingo.trainer.domain.exception.*;
import nl.hu.cisq1.lingo.trainer.presentation.dto.AttemptDTO;
import nl.hu.cisq1.lingo.trainer.presentation.dto.NewGameDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trainer")
public class TrainerController {
    private final TrainerService service;

    public TrainerController(TrainerService service) {
        this.service = service;
    }

    @PostMapping("/game")
    public NewGameDTO createNewGame() {
        return new NewGameDTO(this.service.startNewGame());
    }

    @PostMapping("/game/{id}/round")
    public Progress createNewRound(@PathVariable Long id) throws GameNotFoundException, LostGameException, RoundPlayingException {
        return service.startNewRound(id);
    }

    @PostMapping("/game/{id}/guess")
    public Progress guess(@PathVariable Long id, @RequestBody AttemptDTO attemptDTO) throws GameNotFoundException, LostGameException, RoundHasNotBeenStartedException {
        return service.guess(id,attemptDTO.getAttempt());
    }
}
