package nl.hu.cisq1.lingo.trainer.domain;

import lombok.*;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidFeedbackException;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@EqualsAndHashCode
@Entity
@NoArgsConstructor
public class Feedback {
    @Id
    @GeneratedValue
    private Long id;

    @ElementCollection
    private List<Mark> marks;
    private String attempt;
    private String wordToGuess;

    public Feedback(String wordToGuess, String attempt) {
        this.wordToGuess = wordToGuess;
        this.attempt = attempt;
        this.marks = makeGuess(attempt, wordToGuess);
    }

    private List<Mark> makeGuess(String attempt, String wordToGuess) {
        String[] attemptLetters = attempt.split("");
        String[] answerLetters = wordToGuess.split("");
        List<Mark> markList = new ArrayList<>();

        if (answerLetters.length != attemptLetters.length) {
            isWordInvalid();
        }

        List<String> presentLetters = new ArrayList<>();

        for (int i = 0; i < answerLetters.length; i++) {
            if (answerLetters[i].equals(attemptLetters[i])) {
                markList.add(Mark.CORRECT);
            } else {
                presentLetters.add(answerLetters[i]);
                markList.add(null);
            }
        }

        for (int i = 0; i < answerLetters.length; i++) {
            if (markList.get(i) != Mark.CORRECT) {
                if (presentLetters.contains(attemptLetters[i])) {
                    markList.set(i, Mark.PRESENT);
                    presentLetters.remove(attemptLetters[i]);
                } else {
                    markList.set(i, Mark.ABSENT);
                }
            }
         }

        return markList;
    }

    public boolean isWordGuessed() {
        return this.marks.stream()
                .allMatch(Mark.CORRECT::equals);
    }

    public void isWordInvalid() {
        throw new InvalidFeedbackException();
    }

    public String getHint(String oldHint) {
        String woord = "";
        String[] letters = this.wordToGuess.split("");

        if (oldHint == null) {
            oldHint = letters[0] + ".".repeat(letters.length - 1);
        }

        String[] oldHintLetters = oldHint.split("");

        for (int i = 0; i < letters.length; i++) {
            if (this.marks.get(i).equals(Mark.CORRECT)) {
                woord += letters[i];
            } else {
                woord += oldHintLetters[i];
            }
        }
        return woord;
    }
}
