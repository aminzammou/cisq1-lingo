package nl.hu.cisq1.lingo.trainer.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class Progress {
    private int score;
    private List<String> hints;
    private int roundNumber;
    private GameState status;

    public String getCurrentHint(){
        return hints.get(hints.size() - 1);
    }
}
