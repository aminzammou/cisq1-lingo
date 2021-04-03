package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.hu.cisq1.lingo.trainer.domain.exception.GameEndedException;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Round {
    @Id
    @GeneratedValue
    private Long id;

    private String wordToGuess;
    private int roundNumber;
    private GameState status;
    private String hint;

    @ElementCollection
    private List<String> attempts;

    @OneToMany
    @Cascade(CascadeType.ALL)
    private List<Feedback> history;



    public Round(String wordToGuess) {
        this.wordToGuess = wordToGuess;
        this.roundNumber = 0;
        this.status = GameState.PLAYING;
        this.history = new ArrayList<>();
        this.attempts = new ArrayList<>();
        setFirstHint();
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

        if (this.history.size() > 0) {
            Feedback feedback = history.get(history.size() - 1);
            this.hint = feedback.getHint(this.hint);
        }
        this.attempts.add(this.hint);

        return this.hint;
    }

    public void setFirstHint() {
        String[] lettersToGuess = wordToGuess.split("");
        this.hint = lettersToGuess[0] + ".".repeat(lettersToGuess.length - 1);
    }

    public int getCurrentWordLength() {
        String[] lettersToGuess = wordToGuess.split("");
        return lettersToGuess.length;
    }

    public int getAttemptLength() {
        return this.attempts.size();
    }
}