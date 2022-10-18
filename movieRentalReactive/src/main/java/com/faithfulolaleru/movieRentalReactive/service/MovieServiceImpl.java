package com.faithfulolaleru.movieRentalReactive.service;

import com.faithfulolaleru.movieRentalReactive.dto.MovieRequest;
import com.faithfulolaleru.movieRentalReactive.exception.ErrorResponse;
import com.faithfulolaleru.movieRentalReactive.exception.GeneralException;
import com.faithfulolaleru.movieRentalReactive.models.Movie;
import com.faithfulolaleru.movieRentalReactive.repository.MovieRepository;
import com.faithfulolaleru.movieRentalReactive.response.AppResponse;
import com.faithfulolaleru.movieRentalReactive.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
@Slf4j
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;


    @Override
    public Mono<AppResponse> createMovie(MovieRequest request) {

        // Mono<Movie> movieByTitle = movieRepository.findMovieByTitle(request.getTitle());
        // Optional<Movie> movieOptional = movieByTitle.blockOptional();

        return movieRepository.findMovieByTitle(request.getTitle())
                .map(movie -> throwErrorIfExist(movie))
                .switchIfEmpty(movieRepository.save(Movie.builder()
                        .title(request.getTitle())
                        .yearReleased(request.getYearReleased())
                        .mainActor(request.getMainActor())
                        .isBlockBuster(request.getIsBlockBuster())
                        .perDayCost(request.getPerDayCost())
                        .build()))
                .map(AppUtils::entityToDto2)
                .flatMap(o -> AppUtils.buildAppResponse(o, "Created Successfully"));
                // .subscribe();

            // or use movieRepository.save(AppUtils.dtoToEntity)
    }

    @Override
    public Mono<AppResponse> getAllMovies() {
        return movieRepository.findAll()
                .map(m -> AppUtils.entityToDto2(m))
                .collectList()
                .flatMap(o -> AppUtils.buildAppResponse(o, "Successful"))
                .switchIfEmpty(Mono.empty());   //  AppUtils::entityToDto;
    }

    @Override
    public Mono<AppResponse>  getMovieById(String id) {
        return movieRepository.findById(id)
                .map(movie -> AppUtils.entityToDto2(movie))
                .flatMap(o -> AppUtils.buildAppResponse(o, "Successful"))
                .switchIfEmpty(Mono.error(new GeneralException(HttpStatus.NOT_FOUND,
                        ErrorResponse.ERROR_MOVIE_NOT_EXIST,
                        "Movie with id doesn't exist")));    // Mono.empty()
    }

    @Override
    public Mono<AppResponse>  getAllMoviesBetween(int startYear, int endYear) {
        return movieRepository.findMoviesByYearReleasedBetween(startYear, endYear)
                .map(m -> AppUtils.entityToDto2(m))
                .collectList()
                .flatMap(o -> AppUtils.buildAppResponse(o, "Successful"))
                .switchIfEmpty(Mono.empty());     // Range.closed(startYear, endYear)
    }

    @Override
    public Mono<AppResponse> updateMovieById(MovieRequest request, String id) {

        return movieRepository.findById(id)
                .flatMap(movie -> updateMovie(request, movie))
                .map(AppUtils::entityToDto2)
                .flatMap(o -> AppUtils.buildAppResponse(o, "Updated Successfully"))
                .switchIfEmpty(Mono.error(new GeneralException(HttpStatus.NOT_FOUND,
                        ErrorResponse.ERROR_MOVIE_NOT_EXIST,
                        "Movie with id doesn't exist")));

                // .map(movie -> throwErrorIfNotExist(movie))
    }

    @Override
    public Mono<AppResponse> deleteMovieById(String id) {

        return movieRepository.findById(id)
                .flatMap(movie -> movieRepository.deleteById(movie.getId()).thenReturn(movie))
                .flatMap(o -> AppUtils.buildAppResponse(o, "Successful"))
                .switchIfEmpty(Mono.error(new GeneralException(HttpStatus.NOT_FOUND,
                ErrorResponse.ERROR_MOVIE_NOT_EXIST,
                "Movie with id doesn't exist")));



//        return Mono.justOrEmpty(movieRepository.findById(id))
//                .flatMap(movie -> deleteMovie(id, movie))
//                .then(buildAppResponse("Deleted Successfully"));

        /*  latest comment

        movieRepository.findById(id)
                .map(movie -> AppUtils.entityToDto2(movie))
                .flatMap(o -> AppUtils.buildAppResponse(o, "Successful"))
                .switchIfEmpty(Mono.error(new GeneralException(HttpStatus.NOT_FOUND,
                        ErrorResponse.ERROR_MOVIE_NOT_EXIST,
                        "Movie with id doesn't exist")));

        return movieRepository.deleteById(id);

        */

    /*    return movieRepository.findById(id)
                .switchIfEmpty(Mono.error(new GeneralException(HttpStatus.NOT_FOUND,
                        ErrorResponse.ERROR_MOVIE_NOT_EXIST,
                        "Movie with id doesn't exist")))
                .flatMap(movie -> movieRepository.deleteById(id));*/


        // return movieRepository.deleteById(id);
                //.flatMap()
                // .switchIfEmpty(AppUtils.buildAppResponse("Successfully Deleted"));

        /*return movieRepository.findById(id)
                .map(movie -> throwErrorIfNotExist(movie))
                .flatMap(movie -> movieRepository.delete(movie))
                .flatMap(o -> AppUtils.buildAppResponse("Successfully Deleted"));*/


//                .switchIfEmpty(Mono.error(new GeneralException(HttpStatus.NOT_FOUND,
//                        ErrorResponse.ERROR_MOVIE_NOT_EXIST,
//                        "Movie with id doesn't exist")));


                // .flatMap(movie -> deleteMovie(id, movie));
                // .map(AppUtils::entityToDto2)
                // .flatMap(o -> AppUtils.buildAppResponse("Deleted Successfully"));
                //.flatMap(o -> buildAppResponse("Deleted Successfully"));

                //.then(movieRepository.deleteById(id)); // log.info("Successfully deleted movie with id --> {}", id)
                // .then(AppUtils::entityToDto2)
        // return buildAppResponse("Deleted Successfully");


//                .switchIfEmpty(Mono.error(new GeneralException(HttpStatus.NOT_FOUND,
//                        ErrorResponse.ERROR_MOVIE_NOT_EXIST,
//                        "Movie with id doesn't exist")));

    }

    private Movie throwErrorIfExist(Movie movie) {
        String message = "Movie with title '" + movie.getTitle() + "' already exists";
        throw new GeneralException(HttpStatus.CONFLICT, ErrorResponse.ERROR_MOVIE_ALREADY_EXIST, message);
    }
    private Movie throwErrorIfNotExist(Movie movie) {
        String message = "Movie with title '" + movie.getTitle() + "' does not exist";
        throw new GeneralException(HttpStatus.NOT_FOUND, ErrorResponse.ERROR_MOVIE_NOT_EXIST, message);
    }

    private Mono<Movie> updateMovie(MovieRequest request, Movie movie) {
        Movie newMovie = Movie.builder()
                .id(movie.getId())
                .title((request.getTitle() != null) ? request.getTitle() : movie.getTitle())
                .isBlockBuster((request.getIsBlockBuster() != null) ? request.getIsBlockBuster() : movie.getIsBlockBuster())
                .mainActor((request.getMainActor() != null) ? request.getMainActor() : movie.getMainActor())
                .yearReleased((request.getYearReleased() != null) ? request.getYearReleased() : movie.getYearReleased())
                .perDayCost((request.getPerDayCost() != null) ? request.getPerDayCost() : movie.getPerDayCost())
                .build();

        return movieRepository.save(newMovie);
    }

    private Mono<Void> deleteMovie(String id, Movie movie) {

        Mono<Movie> movieMono = movieRepository.findById(id)
                .switchIfEmpty(Mono.error(new GeneralException(HttpStatus.NOT_FOUND,
                        ErrorResponse.ERROR_MOVIE_NOT_EXIST,
                        "Movie with id doesn't exist")));
        log.info("Successfully deleted movie with id --> {}", id);

        return movieRepository.deleteById(id);   // movie.block().getId()
        // movieRepository.delete(movie);


        // return movieMono;
        // String message = "Successfully deleted movie with id";

        //return Mono.just(message);
    }

}
