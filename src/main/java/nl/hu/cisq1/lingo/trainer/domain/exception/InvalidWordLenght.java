package nl.hu.cisq1.lingo.trainer.domain.exception;

public class InvalidWordLenght extends RuntimeException{
    public InvalidWordLenght() {
        super("Invalid lenght of letters");
    }
}
