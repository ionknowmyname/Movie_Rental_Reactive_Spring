package com.faithfulolaleru.movieRentalReactive.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppResponse {

    private String statusCode;
    private HttpStatus httpStatus;
    private String message;
    private Object data;
}
