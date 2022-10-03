package com.faithfulolaleru.movieRentalReactive.service;

import com.faithfulolaleru.movieRentalReactive.dto.CreateMovieRequest;
import com.faithfulolaleru.movieRentalReactive.dto.CreateMovieResponse;
import com.faithfulolaleru.movieRentalReactive.models.Movie;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovieService {

    public Mono<CreateMovieResponse> createMovie(CreateMovieRequest request);

    public Mono<CreateMovieResponse> getMovieById(String id);

    public Flux<CreateMovieResponse> getAllMovies();

    public Mono<CreateMovieResponse> updateMovieById(CreateMovieRequest request, String id);

    public Mono<Void> deleteMovieById(String id);

}
