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

        this.gameRepository.save(game);
        String wordToGuess = this.wordService.provideRandomWord(game.getWordLength());
        return game.startNewGame(wordToGuess);
    }


}
