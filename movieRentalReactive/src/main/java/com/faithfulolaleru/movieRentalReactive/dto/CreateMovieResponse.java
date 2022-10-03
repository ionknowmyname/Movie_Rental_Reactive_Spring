package com.faithfulolaleru.movieRentalReactive.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateMovieResponse {

    private String id;
    private String title;
    private int yearReleased;
    private String mainActor;
    private boolean isBlockBuster;
}
