package nl.hu.cisq1.lingo.trainer.application;

import lombok.AllArgsConstructor;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.words.application.WordService;

@AllArgsConstructor
public class TrainerService {
    private WordService wordService;
    private SpringGameRepository gameRepository;

    public Long startNewGame() {
        Game game = new Game();
//        GameEntity gameEntity = new GameEntity(game);
        this.gameRepository.save(game);

        return game.getId();
    }

}
