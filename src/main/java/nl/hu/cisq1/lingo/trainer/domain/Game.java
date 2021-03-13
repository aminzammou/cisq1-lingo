package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Getter;
import nl.hu.cisq1.lingo.trainer.domain.exception.GameHasNotBeenStartedExeption;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidWordLength;
import nl.hu.cisq1.lingo.trainer.domain.exception.RoundPlayingExeption;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Game {
    private int score;
    private List<Round> rounds;
    private int wordLength;

    public Game() {
        this.score = 0;
        this.wordLength = 5;
        this.rounds = new ArrayList<>();
    }

    public String startNewGame(String wordToGuess) {
        if (wordToGuess.length() != wordLength) {
            throw new InvalidWordLength();
        }

        if (getAmountOfRounds() == 1) {
            if (rounds.get(rounds.size() - 1).getStatus() == GameState.PLAYING) {
                throw new RoundPlayingExeption();
            }
        }

        Round round = new Round(wordToGuess);
        rounds.add(round);
        getNextWordLength();

        return round.firstHint();
    }

    public String guess(String guess) {
        if (getAmountOfRounds() == 0) {
            throw new GameHasNotBeenStartedExeption();
        }

        Round round = rounds.get(rounds.size() - 1);
        calculateScore();

        return round.guessing(guess);
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


}
