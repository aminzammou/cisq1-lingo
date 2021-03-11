package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Getter;
import net.bytebuddy.implementation.bytecode.Throw;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidWordLenght;
import nl.hu.cisq1.lingo.trainer.domain.exception.RoundPlayingExeption;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Game {
    private int score;
    private List<Round> rondes;
    private int wordLenght;

    public Game(){
        this.score = 0;
        this.wordLenght = 5;
        this.rondes = new ArrayList<>();
    }

    public String StartNewGame(String wordToGuess){
        if (wordToGuess.length() != wordLenght){
            throw new InvalidWordLenght();
        }
        if (getAmountOfRoundes() == 1){
            if (rondes.get(rondes.size() - 1).getStatus() == GameState.PLAYING ){
                throw new RoundPlayingExeption();
            }
        }

        Round round = new Round(wordToGuess);
        rondes.add(round);
        getNextWordLenght();
        return round.firstHint();
    }

    public String guess(String guess){
        Round round = rondes.get(rondes.size() -1);
        calculateScore();
        return round.guessing(guess);
    }

    public void getNextWordLenght(){
        int wordToGuessLenght = rondes.get(rondes.size() -1).getCurrentWordLenght();
        if (wordToGuessLenght == 7){
            this.wordLenght = 5;
        }else if (wordToGuessLenght == 6){
            this.wordLenght = 7;
        }else {
            this.wordLenght = 6;
        }
    }

    public void calculateScore(){
        Round round = rondes.get(rondes.size() -1);
        this.score = 5 * (5 - round.getAttemptLenght()) * 5;
    }

    public int getAmountOfRoundes(){
        return rondes.size();
    }



}
