package com.faithfulolaleru.movieRentalReactive.controllers;

import com.faithfulolaleru.movieRentalReactive.dto.MovieRequest;
import com.faithfulolaleru.movieRentalReactive.dto.MovieResponse;
import com.faithfulolaleru.movieRentalReactive.response.AppResponse;
import com.faithfulolaleru.movieRentalReactive.service.MovieService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/movies")
@Data
public class MovieController {

    private final MovieService movieService;


    @GetMapping("/")
    public Mono<AppResponse> getAllMovies(){

        return movieService.getAllMovies();
    }
    @PostMapping("/")
    public Mono<ResponseEntity<MovieResponse>> createMovie(@RequestBody MovieRequest request) {

        return movieService.createMovie(request).map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public Mono<AppResponse> getAllMovies(@PathVariable("id") String id){

        // return movieService.getMovieById(id).map(ResponseEntity::ok);
        return movieService.getMovieById(id);
    }

    @GetMapping("/between")
    public Mono<AppResponse>  getAllMoviesBetweenYear(@RequestParam(value = "startDate") int startDate,
                                                      @RequestParam(value = "endDate") int endDateDate) {

        return movieService.getAllMoviesBetween(startDate, endDateDate);   // .map(ResponseEntity::ok);
    }


}
