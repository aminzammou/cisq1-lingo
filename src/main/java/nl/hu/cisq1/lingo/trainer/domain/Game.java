package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Getter;
import nl.hu.cisq1.lingo.trainer.domain.exception.GameHasNotBeenStartedExeption;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidWordLength;
import nl.hu.cisq1.lingo.trainer.domain.exception.RoundPlayingExeption;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Game implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    private int score;

    @OneToMany
    @Cascade(CascadeType.ALL)
    private final List<Round> rounds;

    private int wordLength;
    private String currentHint;

    public Game() {
        this.score = 0;
        this.wordLength = 5;
        this.rounds = new ArrayList<>();
    }

    public Progress startNewGame(String wordToGuess) {
        if (wordToGuess.length() != wordLength) {
            throw new InvalidWordLength();
        }

        if (getAmountOfRounds() >= 1) {
            if (rounds.get(rounds.size() - 1).getStatus() == GameState.PLAYING) {
                throw new RoundPlayingExeption();
            }
        }
        Round round = new Round(wordToGuess);
        rounds.add(round);
        getNextWordLength();
        round.firstHint();

        return showProgress();
    }

    public Progress guess(String guess) {
        if (getAmountOfRounds() == 0) {
            throw new GameHasNotBeenStartedExeption();
        }

        Round round = rounds.get(rounds.size() - 1);
        calculateScore();
        round.guessing(guess);

        return showProgress();
    }

    public void getNextWordLength() {
        int wordToGuessLength = rounds.get(rounds.size() - 1).getCurrentWordLength();

        if (wordToGuessLength == 7) {
            this.wordLength = 5;
        } else if (wordToGuessLength == 6) {
            this.wordLength = 7;
        } else {
            this.wordLength = 6;
        }
    }

    public void calculateScore() {
        Round round = rounds.get(rounds.size() - 1);
        this.score = 5 * (5 - round.getAttemptLength()) + 5;
    }

    public int getAmountOfRounds() {
        return rounds.size();
    }

    public List<String> getAttampts() {
        return this.rounds.get(getAmountOfRounds() - 1).getAttempts();
    }



    private Progress showProgress() {
        return new Progress(this.score, getAttampts(),getAmountOfRounds());
    }
}
