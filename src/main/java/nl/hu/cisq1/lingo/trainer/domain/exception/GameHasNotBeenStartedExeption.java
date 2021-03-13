package nl.hu.cisq1.lingo.trainer.domain.exception;

public class GameHasNotBeenStartedExeption extends RuntimeException {
    public GameHasNotBeenStartedExeption() {
        super("You can not guess yet, the game has not been started!");
    }
}
