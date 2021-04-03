package nl.hu.cisq1.lingo.trainer.domain.exception;

public class RoundHasNotBeenStartedExeption extends RuntimeException {
    public RoundHasNotBeenStartedExeption() {
        super("You can not guess yet, the round has not been started!");
    }
}
