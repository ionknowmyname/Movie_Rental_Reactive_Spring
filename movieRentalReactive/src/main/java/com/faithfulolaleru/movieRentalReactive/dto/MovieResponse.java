package com.faithfulolaleru.movieRentalReactive.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieResponse {

    private String id;
    private String title;
    private Integer yearReleased;
    private String mainActor;
    private Boolean isBlockBuster;
    private Double perDayCost;
}
