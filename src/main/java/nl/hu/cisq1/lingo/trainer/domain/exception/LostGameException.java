package nl.hu.cisq1.lingo.trainer.domain.exception;

import javassist.NotFoundException;

public class LostGameException extends NotFoundException {
    public LostGameException(String msg) {
        super(msg);
    }
}