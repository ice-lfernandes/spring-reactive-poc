package br.com.ldf.springreactivepoc.api.advice;

import br.com.ldf.springreactivepoc.api.dto.error.InputValidationErrorResponse;
import br.com.ldf.springreactivepoc.exception.InputValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdviceHandler {

    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<InputValidationErrorResponse> handleException(InputValidationException exception) {
        return ResponseEntity.badRequest().body(
                new InputValidationErrorResponse(
                        exception.getErrorCode(),
                        exception.getInput(),
                        exception.getMessage()
                )
        );
    }
}
