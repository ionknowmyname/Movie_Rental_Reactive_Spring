package com.faithfulolaleru.movieRentalReactive.service;

import com.faithfulolaleru.movieRentalReactive.dto.MovieRequest;
import com.faithfulolaleru.movieRentalReactive.dto.MovieResponse;
import com.faithfulolaleru.movieRentalReactive.exception.GeneralException;
import com.faithfulolaleru.movieRentalReactive.models.Movie;
import com.faithfulolaleru.movieRentalReactive.repository.MovieRepository;
import com.faithfulolaleru.movieRentalReactive.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Range;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;


    @Override
    public Mono<MovieResponse> createMovie(MovieRequest request) {
        Mono<Boolean> booleanMono = movieRepository.existsByTitle(request.getTitle());

        log.debug("booleanMono:  {}", booleanMono);

        movieRepository.existsByTitle(request.getTitle())
                .doOnNext(exists -> {
                    if (exists) {
                        throw new GeneralException("Movie with id already exist",
                                HttpStatus.FORBIDDEN);
                    }
                });

        Mono<Movie> movieByTitle = movieRepository.findMovieByTitle(request.getTitle());
        movieByTitle.();

        return movieRepository.findMovieByTitle(request.getTitle())
                .switchIfEmpty(movieRepository.save(Movie.builder()
                        .title(request.getTitle())
                        .yearReleased(request.getYearReleased())
                        .mainActor(request.getMainActor())
                        .isBlockBuster(request.isBlockBuster())
                        .build()))
                .map(AppUtils::entityToDto2);
                // .subscribe();

            // or use movieRepository.save(AppUtils.dtoToEntity)

            // ot use movieRepository.save(Movie.builder())
    }

    @Override
    public Mono<MovieResponse> getMovieById(String id) {
        return movieRepository.findById(id)
                .map(m -> AppUtils.entityToDto2(m))
                .switchIfEmpty(Mono.error(new GeneralException("Movie with id doesn't exist",
                        HttpStatus.NOT_FOUND)));    // Mono.empty()
    }

    @Override
    public Flux<MovieResponse> getAllMovies() {
        return movieRepository.findAll()
                .map(m -> AppUtils.entityToDto2(m))
                .switchIfEmpty(Flux.empty());   //  AppUtils::entityToDto;
    }

    @Override
    public Flux<MovieResponse> getAllMoviesBetween(int startYear, int endYear) {
        return movieRepository.findMoviesByYearReleasedBetween(startYear, endYear)
                .map(m -> AppUtils.entityToDto2(m))
                .switchIfEmpty(Flux.empty());     // Range.closed(startYear, endYear)
    }

    @Override
    public Mono<MovieResponse> updateMovieById(MovieRequest request, String id) {
        Mono<Movie> foundMovie = movieRepository.findById(id);

        // if(foundMovie.)

        return null;
    }

    @Override
    public Mono<Void> deleteMovieById(String id) {
        return null;
    }


    private boolean isPresent(String id) {
        return false;
    }
}
