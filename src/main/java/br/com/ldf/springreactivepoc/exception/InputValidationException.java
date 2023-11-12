package br.com.ldf.springreactivepoc.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;


@NoArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InputValidationException extends RuntimeException {
    static final String MSG = "allowed range is 10 - 20";
    int errorCode = 100;
    int input;

    public InputValidationException(int input) {
        super(MSG);
        this.input = input;
    }
}
