package nl.hu.cisq1.lingo.trainer.application;

import lombok.AllArgsConstructor;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.Progress;
import nl.hu.cisq1.lingo.words.application.WordService;

@AllArgsConstructor
public class TrainerService {
    private final WordService wordService;
    private final SpringGameRepository gameRepository;

    public Progress startNewGame() {
        Game game = new Game();
        String wordToGuess = this.wordService.provideRandomWord(game.getWordLength());
        Progress progress = game.startNewGame(wordToGuess);
        this.gameRepository.save(game);

        return progress;
    }

    public Progress guess(long id,String guess) {
        Game game = this.gameRepository.findById(id).orElseThrow(() -> new RuntimeException("Game Not found"));
        Progress progress = game.guess(guess);
        this.gameRepository.save(game);

        return progress;
    }


}
