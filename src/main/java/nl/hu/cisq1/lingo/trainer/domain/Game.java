package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Getter;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidWordLength;
import nl.hu.cisq1.lingo.trainer.domain.exception.RoundHasNotBeenStartedException;
import nl.hu.cisq1.lingo.trainer.domain.exception.RoundPlayingException;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
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

    public Progress startNewRound(String wordToGuess) {
        if (wordToGuess.length() != wordLength) {
            throw new InvalidWordLength();
        }

        if (getAmountOfRounds() >= 1) {
            if (rounds.get(rounds.size() - 1).getStatus() == GameState.PLAYING) {
                throw new RoundPlayingException();
            }
        }
        Round round = new Round(wordToGuess);
        this.rounds.add(round);
        getNextWordLength();
        currentHint = round.getHint();

        return showProgress();
    }

    public Progress guess(String guess) {
        if (getAmountOfRounds() == 0) {
            throw new RoundHasNotBeenStartedException();
        }

        Round round = rounds.get(rounds.size() - 1);
        calculateScore();
        currentHint = round.guessing(guess);

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
        this.score = 5 * (5 - round.getAttemptLength() - 1) + 5;
    }

    public int getAmountOfRounds() {
        return rounds.size();
    }


    public Round getCurrentRound(){
        if (rounds.isEmpty()){
            return null;
        }else{
            return rounds.get(rounds.size() - 1);
        }
    }
    public GameState getStatus(){
        return getCurrentRound().getStatus();
    }

    private Progress showProgress() {
        return new Progress(id,this.score,getAmountOfRounds(),getStatus(),getCurrentRound().getHistory(),currentHint);
    }
}
