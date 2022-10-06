package com.faithfulolaleru.movieRentalReactive.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieRequest {

    private String title;
    private Integer yearReleased;
    private String mainActor;
    private Boolean isBlockBuster = false;
    private Double perDayCost;
}
