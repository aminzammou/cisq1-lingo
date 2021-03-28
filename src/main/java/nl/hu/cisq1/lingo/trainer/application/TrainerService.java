package nl.hu.cisq1.lingo.trainer.application;

import lombok.AllArgsConstructor;
import net.bytebuddy.implementation.bytecode.Throw;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.GameState;
import nl.hu.cisq1.lingo.trainer.domain.Progress;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TrainerService {
    private final WordService wordService;
    private final SpringGameRepository gameRepository;

    public Long startNewGame() {
        Game game = new Game();

        this.gameRepository.save(game);

        return game.getId();
    }

    public Progress startNewRound(Long id) {
        Game game = this.gameRepository.findById(id).orElseThrow(() -> new RuntimeException("Game Not found"));
        if (game.getStatus().equals(GameState.ELIMINATED)){
            throw new RuntimeException("You cant play, you lost this game !");
        }

        String wordToGuess = this.wordService.provideRandomWord(game.getWordLength());
        Progress progress = game.startNewGame(wordToGuess);
        this.gameRepository.save(game);

        return progress;
    }

    public Progress guess(Long id, String guess) {
        Game game = this.gameRepository.findById(id).orElseThrow(() -> new RuntimeException("Game Not found"));
        Progress progress = game.guess(guess);
        this.gameRepository.save(game);

        return progress;
    }


}
