package br.com.ldf.springreactivepoc.api.dto.error;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InputValidationErrorResponse {
    int errorCode;
    int input;
    String message;
}
