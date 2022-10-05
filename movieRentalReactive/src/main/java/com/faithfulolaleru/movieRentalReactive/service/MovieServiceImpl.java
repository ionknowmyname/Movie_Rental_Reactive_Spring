package com.faithfulolaleru.movieRentalReactive.service;

import com.faithfulolaleru.movieRentalReactive.dto.MovieRequest;
import com.faithfulolaleru.movieRentalReactive.dto.MovieResponse;
import com.faithfulolaleru.movieRentalReactive.exception.ErrorResponse;
import com.faithfulolaleru.movieRentalReactive.exception.GeneralException;
import com.faithfulolaleru.movieRentalReactive.models.Movie;
import com.faithfulolaleru.movieRentalReactive.repository.MovieRepository;
import com.faithfulolaleru.movieRentalReactive.response.AppResponse;
import com.faithfulolaleru.movieRentalReactive.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Range;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;


    @Override
    public Mono<MovieResponse> createMovie(MovieRequest request) {

        // Mono<Movie> movieByTitle = movieRepository.findMovieByTitle(request.getTitle());
        // Optional<Movie> movieOptional = movieByTitle.blockOptional();

        return movieRepository.findMovieByTitle(request.getTitle())
                .map(movie -> throwErrorIfExist(movie))
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
    public Mono<AppResponse>  getMovieById(String id) {
        return movieRepository.findById(id)
                .map(movie -> AppUtils.entityToDto2(movie))
                .flatMap(o -> buildAppResponse(o))
                .switchIfEmpty(Mono.error(new GeneralException(HttpStatus.NOT_FOUND,
                        ErrorResponse.ERROR_MOVIE_NOT_EXIST,
                        "Movie with id doesn't exist")));    // Mono.empty()
    }

    @Override
    public Mono<AppResponse> getAllMovies() {
        return movieRepository.findAll()
                .map(m -> AppUtils.entityToDto2(m))
                .collectList()
                .flatMap(o -> buildAppResponse(o))
                .switchIfEmpty(Mono.empty());   //  AppUtils::entityToDto;
    }

    @Override
    public Mono<AppResponse>  getAllMoviesBetween(int startYear, int endYear) {
        return movieRepository.findMoviesByYearReleasedBetween(startYear, endYear)
                .map(m -> AppUtils.entityToDto2(m))
                .collectList()
                .flatMap(o -> buildAppResponse(o))
                .switchIfEmpty(Mono.empty());     // Range.closed(startYear, endYear)
    }

    @Override
    public Mono<MovieResponse> updateMovieById(MovieRequest request, String id) {
        Mono<Movie> foundMovie = movieRepository.findById(id);

        // if(foundMovie.)

        return null;
    }

    @Override
    public Mono<Void> deleteMovieById(String id) {

        return Mono.justOrEmpty(movieRepository.findById(id)).doOnNext(movie -> {
            movieRepository.deleteById(movie.block().getId());
            // movieRepository.delete(movie.subscribe());
            log.info("Successfully deleted movie with id --> {}", id);
        }).then(Mono.empty());
    }


    private Movie throwErrorIfExist(Movie movie) {
        String message = "Movie with title '" + movie.getTitle() + "' already exists";
        throw new GeneralException(HttpStatus.CONFLICT, ErrorResponse.ERROR_MOVIE_ALREADY_EXIST, message);
    }
    private Movie throwErrorIfNotExist(Movie movie) {
        String message = "Movie with title '" + movie.getTitle() + "' does not exist exists";
        throw new GeneralException(HttpStatus.NOT_FOUND, ErrorResponse.ERROR_MOVIE_ALREADY_EXIST, message);
    }

    private Mono<AppResponse> buildAppResponse(Object movies) {   // Object can also be List
        return Mono.just(AppResponse.builder()
                        .statusCode("200")
                        .httpStatus(HttpStatus.OK)
                        .message("Successful")
                        .data(movies)
                        .build());
    }
}
