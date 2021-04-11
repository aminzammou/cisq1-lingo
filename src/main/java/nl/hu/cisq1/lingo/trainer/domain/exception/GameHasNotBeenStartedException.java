package nl.hu.cisq1.lingo.trainer.domain.exception;

public class GameHasNotBeenStartedException extends RuntimeException {
    public GameHasNotBeenStartedException() {
        super("You can not guess yet, the game has not been started!");
    }
}
