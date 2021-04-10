package nl.hu.cisq1.lingo.trainer.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExeptionHandler {

    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity<ExceptionResponse> GameNotFoundException(GameNotFoundException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorCode("NOT FOUND");
        exceptionResponse.setErrorMessage(ex.getMessage());
        exceptionResponse.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LostGameException.class)
    public ResponseEntity<ExceptionResponse> LostGameException(LostGameException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorCode("NOT FOUND");
        exceptionResponse.setErrorMessage(ex.getMessage());
        exceptionResponse.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(RoundPlayingExeption.class)
    public ResponseEntity<ExceptionResponse> RoundPlayingExeption(RoundPlayingExeption ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorCode("CONFLICT");
        exceptionResponse.setErrorMessage(ex.getMessage());
        exceptionResponse.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RoundHasNotBeenStartedExeption.class)
    public ResponseEntity<ExceptionResponse> RoundHasNotBeenStartedExeption(RoundHasNotBeenStartedExeption ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorCode("CONFLICT");
        exceptionResponse.setErrorMessage(ex.getMessage());
        exceptionResponse.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.CONFLICT);
    }
}
