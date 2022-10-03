package com.faithfulolaleru.movieRentalReactive.repository;

import com.faithfulolaleru.movieRentalReactive.models.Movie;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface MovieRepository extends ReactiveMongoRepository<Movie, String> {

    Mono<Movie> findMovieByTitle(String title);
}
