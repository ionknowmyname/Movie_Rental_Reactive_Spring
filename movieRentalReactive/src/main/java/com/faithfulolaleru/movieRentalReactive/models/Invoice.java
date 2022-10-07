package com.faithfulolaleru.movieRentalReactive.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "invoices-reactive")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {

    @Id
    private String id;

    @DBRef
    private Movie movie;

    @DBRef
    private User user;

    private Instant rentStartDate;
    private Integer rentDays;
    private Double rentCost;
}
