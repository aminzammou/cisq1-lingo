package nl.hu.cisq1.lingo.trainer.domain.exception;

public class RoundPlayingException extends RuntimeException {
    public RoundPlayingException() {
        super("Round is still playing");
    }
}
