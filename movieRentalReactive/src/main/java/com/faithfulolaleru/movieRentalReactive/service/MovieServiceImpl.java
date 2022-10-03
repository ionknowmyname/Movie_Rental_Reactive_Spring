package com.faithfulolaleru.movieRentalReactive.service;

import com.faithfulolaleru.movieRentalReactive.dto.CreateMovieRequest;
import com.faithfulolaleru.movieRentalReactive.dto.CreateMovieResponse;
import com.faithfulolaleru.movieRentalReactive.exception.GeneralException;
import com.faithfulolaleru.movieRentalReactive.models.Movie;
import com.faithfulolaleru.movieRentalReactive.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;


    @Override
    public Mono<CreateMovieResponse> createMovie(CreateMovieRequest request) {
        return movieRepository.findMovieByTitle(request.getTitle())
                .switchIfEmpty(movieRepository.save(Movie.builder()
                        .title(request.getTitle())
                        .yearReleased(request.getYearReleased())
                        .mainActor(request.getMainActor())
                        .isBlockBuster(request.isBlockBuster())
                        .build()));
                // .subscribe();
    }

    @Override
    public Mono<CreateMovieResponse> getMovieById(String id) {
        return movieRepository.findById(id)
                .switchIfEmpty(Mono.error(new GeneralException("Movie with id doesn't exist",
                        HttpStatus.NOT_FOUND)));    // Mono.empty()
    }

    @Override
    public Flux<CreateMovieResponse> getAllMovies() {
        return movieRepository.findAll().switchIfEmpty(Flux.empty());
    }

    @Override
    public Mono<CreateMovieResponse> updateMovieById(CreateMovieRequest request, String id) {
        Mono<Movie> foundMovie = movieRepository.findById(id);

        if(foundMovie.)
    }

    @Override
    public Mono<Void> deleteMovieById(String id) {
        return null;
    }
}
