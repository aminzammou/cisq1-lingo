package nl.hu.cisq1.lingo.trainer.domain.exception;

public class RoundHasNotBeenStartedException extends RuntimeException {
    public RoundHasNotBeenStartedException() {
        super("You can not guess yet, the round has not been started!");
    }
}
