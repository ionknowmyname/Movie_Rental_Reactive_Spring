package com.faithfulolaleru.movieRentalReactive.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceRequest {

    private String movieTitle;
    private Instant startDate;
    private Integer rentDays;
    private Double rentCost;

    // add user later
}
