package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import nl.hu.cisq1.lingo.trainer.domain.Progress;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trainer")
public class TrainerController {
    private final TrainerService service;

    public TrainerController(TrainerService service) {
        this.service = service;
    }

    @PostMapping("/game")
    public Long createNewGame() {
       return service.startNewGame();
    }

    @PostMapping("/game//{id}")
    public Progress createNewRound(@PathVariable Long id) {
        return service.startNewRound(id);
    }

    @PostMapping("/game//{id}/{attempt}")
    public Progress guess(@PathVariable Long id, @PathVariable String attempt) {
        return service.guess(id,attempt);
    }
}
