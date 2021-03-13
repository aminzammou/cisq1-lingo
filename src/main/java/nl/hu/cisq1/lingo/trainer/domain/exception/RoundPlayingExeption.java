package nl.hu.cisq1.lingo.trainer.domain.exception;

public class RoundPlayingExeption extends RuntimeException {
    public RoundPlayingExeption() {
        super("Round is still playing");
    }
}
