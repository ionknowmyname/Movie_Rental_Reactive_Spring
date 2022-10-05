package com.faithfulolaleru.movieRentalReactive.service;

import com.faithfulolaleru.movieRentalReactive.dto.MovieRequest;
import com.faithfulolaleru.movieRentalReactive.dto.MovieResponse;
import com.faithfulolaleru.movieRentalReactive.response.AppResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovieService {

    public Mono<MovieResponse> createMovie(MovieRequest request);

    public Mono<AppResponse> getMovieById(String id);

    public Mono<AppResponse> getAllMovies();

    public Mono<AppResponse>  getAllMoviesBetween(int startYear, int endYear);

    public Mono<MovieResponse> updateMovieById(MovieRequest request, String id);

    public Mono<Void> deleteMovieById(String id);

}
