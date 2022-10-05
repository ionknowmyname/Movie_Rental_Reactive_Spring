package com.faithfulolaleru.movieRentalReactive.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    public static final String ERROR_MOVIE_ALREADY_EXIST = "MOVIE_ALREADY_EXISTS";

    public static final String ERROR_MOVIE_NOT_EXIST = "MOVIE_DOES_NOT_EXISTS";



    private String error;
    private String message;
    private HttpStatus httpStatus;


    public ErrorResponse(final GeneralException ex) {
        this.error = ex.getError();
        this.message = ex.getMessage();   //  ex.getLocalizedMessage()
        this.httpStatus = ex.getHttpStatus();
    }
}
