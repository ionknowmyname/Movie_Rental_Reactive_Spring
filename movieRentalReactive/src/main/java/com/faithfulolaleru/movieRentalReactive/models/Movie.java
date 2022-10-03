package com.faithfulolaleru.movieRentalReactive.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Size;

@Data
@Document(collection = "movies-reactive")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id
    private String id;
    private String title;

    @Size(max = 4)
    private int yearReleased;
    private String mainActor;
    private boolean isBlockBuster;

}
