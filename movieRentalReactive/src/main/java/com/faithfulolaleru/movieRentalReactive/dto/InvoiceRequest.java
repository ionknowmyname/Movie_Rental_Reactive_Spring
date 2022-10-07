package com.faithfulolaleru.movieRentalReactive.dto;

import com.faithfulolaleru.movieRentalReactive.models.Movie;
import com.faithfulolaleru.movieRentalReactive.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceRequest {

    private Movie movie;
    private Instant rentStartDate;
    private Integer rentDays;
    private Double rentCost;
    private User user;
}
