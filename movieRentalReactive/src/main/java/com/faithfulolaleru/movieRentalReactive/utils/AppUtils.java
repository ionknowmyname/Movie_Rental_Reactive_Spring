package com.faithfulolaleru.movieRentalReactive.utils;

import com.faithfulolaleru.movieRentalReactive.dto.MovieRequest;
import com.faithfulolaleru.movieRentalReactive.dto.MovieResponse;
import com.faithfulolaleru.movieRentalReactive.models.Movie;
import org.springframework.beans.BeanUtils;

public class AppUtils {

    public static MovieResponse entityToDto(Movie movie) {
        MovieResponse response = new MovieResponse();
        BeanUtils.copyProperties(movie, response);

        return response;
    }

    public static MovieResponse entityToDto2(Movie movie) {
        MovieResponse response = MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .yearReleased(movie.getYearReleased())
                .mainActor(movie.getMainActor())
                .isBlockBuster(movie.getIsBlockBuster())
                .build();

        return response;
    }

    public Movie dtoToEntity(MovieRequest request) {
        Movie movie = Movie.builder()
                .title(request.getTitle())
                .yearReleased(request.getYearReleased())
                .mainActor(request.getMainActor())
                .isBlockBuster(request.getIsBlockBuster())
                .build();

        return movie;
    }
}
