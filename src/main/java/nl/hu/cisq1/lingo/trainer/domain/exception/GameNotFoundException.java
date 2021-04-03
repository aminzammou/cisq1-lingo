package nl.hu.cisq1.lingo.trainer.domain.exception;

import javassist.NotFoundException;

public class GameNotFoundException extends NotFoundException {
    public GameNotFoundException(String msg) {
        super(msg);
    }
}
