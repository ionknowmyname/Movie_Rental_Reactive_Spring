package com.faithfulolaleru.movieRentalReactive.utils;

import com.faithfulolaleru.movieRentalReactive.dto.CreateMovieRequest;
import com.faithfulolaleru.movieRentalReactive.dto.CreateMovieResponse;
import com.faithfulolaleru.movieRentalReactive.models.Movie;
import org.springframework.beans.BeanUtils;

public class AppUtils {

    public static CreateMovieResponse entityToDto(Movie movie) {
        CreateMovieResponse response = new CreateMovieResponse();
        BeanUtils.copyProperties(movie, response);

        return response;
    }

    public CreateMovieResponse entityToDto2(Movie movie) {
        CreateMovieResponse response = CreateMovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .yearReleased(movie.getYearReleased())
                .mainActor(movie.getMainActor())
                .isBlockBuster(movie.isBlockBuster())
                .build();

        return response;
    }

    public Movie dtoToEntity(CreateMovieRequest request) {
        Movie movie = Movie.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .yearReleased(movie.getYearReleased())
                .mainActor(movie.getMainActor())
                .isBlockBuster(movie.isBlockBuster())
                .build();

        return movie;
    }
}
