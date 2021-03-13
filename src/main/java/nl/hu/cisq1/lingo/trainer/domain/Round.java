package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Getter;
import lombok.Setter;
import nl.hu.cisq1.lingo.trainer.domain.exception.GameEndedException;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Round {
    private String wordToGuess;
    private int roundNumber;
    private GameState status;
    private List<Feedback> history;
    private ArrayList<String> attempts;

    public Round(String wordToGuess) {
        this.wordToGuess = wordToGuess;
        this.roundNumber = 0;
        this.status = GameState.PLAYING;
        this.history = new ArrayList<>();
        this.attempts = new ArrayList<>();
    }

    public String guessing(String guess) {
        if (this.status != GameState.PLAYING) {
            throw new GameEndedException();
        }

        Feedback feedback = new Feedback(this.wordToGuess, guess);
        this.history.add(feedback);
        String hint = giveHint();

        if (feedback.isWordGuessed()) {
            this.status = GameState.WON;
        } else if (getAttemptLength() == 5) {
            this.status = GameState.ELIMINATED;
        } else {
            this.status = GameState.PLAYING;
        }

        return hint;
    }

    public String giveHint() {
        String hint = firstHint();

        if (this.attempts.size() > 0) {
            Feedback feedback = history.get(history.size() - 1);
            hint = feedback.getHint(this.attempts.get(getAttemptLength() - 1));
        }

        this.attempts.add(hint);

        return hint;
    }

    public String firstHint() {
        String[] lettersToGuess = wordToGuess.split("");
        String hint = lettersToGuess[0] + ".".repeat(lettersToGuess.length - 1);
        return hint;
    }

    public int getCurrentWordLength() {
        String[] lettersToGuess = wordToGuess.split("");
        return lettersToGuess.length;
    }

    public int getAttemptLength() {
        return this.attempts.size();
    }
}
