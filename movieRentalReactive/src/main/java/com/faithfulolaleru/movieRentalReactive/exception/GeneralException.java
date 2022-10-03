package com.faithfulolaleru.movieRentalReactive.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class GeneralException extends RuntimeException {

    private final String message;
    private final HttpStatus httpStatus;


    public GeneralException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
