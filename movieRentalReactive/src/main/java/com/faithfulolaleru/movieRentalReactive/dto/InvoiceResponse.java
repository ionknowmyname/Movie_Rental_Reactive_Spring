package com.faithfulolaleru.movieRentalReactive.dto;

import com.faithfulolaleru.movieRentalReactive.models.Movie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceResponse {

    private String id;
    private Movie movie;
    private Instant rentStartDate;
    private Integer rentDays;
    private Instant expectedReturnDate;
    private Double rentCost;

    // add user later


    public Instant getExpectedReturnDate() {
        return expectedReturnDate;
    }

}
