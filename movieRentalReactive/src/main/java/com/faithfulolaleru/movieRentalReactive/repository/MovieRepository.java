package com.faithfulolaleru.movieRentalReactive.repository;

import com.faithfulolaleru.movieRentalReactive.models.Movie;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface MovieRepository extends ReactiveMongoRepository<Movie, String> {

    Mono<Movie> findMovieByTitle(String title);
    Mono<Boolean> existsByTitle(String title);

    Flux<Movie> findMoviesByYearReleasedBetween(int start, int end);
}
