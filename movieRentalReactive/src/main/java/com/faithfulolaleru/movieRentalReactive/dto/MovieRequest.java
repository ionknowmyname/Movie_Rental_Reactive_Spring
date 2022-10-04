package com.faithfulolaleru.movieRentalReactive.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieRequest {

    private String title;
    private int yearReleased;
    private String mainActor;
    private boolean isBlockBuster;
}
