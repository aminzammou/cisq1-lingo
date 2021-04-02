package nl.hu.cisq1.lingo.trainer.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class Progress {
    private Long gameId;
    private int score;
    private int roundNumber;
    private GameState status;
    private List<Feedback> history;
    private String hint;
}
