package guru.springframework.netflux.controllers;

import guru.springframework.netflux.domain.Movie;
import guru.springframework.netflux.domain.MovieEvent;
import guru.springframework.netflux.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.*;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    // test with terminal curl (not PowerShell) : curl -v http://localhost:8080/movies/{id}
    @GetMapping("/{id}")
    Mono<Movie> getMovieById(@PathVariable String id) {
        return movieService.getMovieById(id);
    }

    // test with terminal curl (not PowerShell) : curl -v http://localhost:8080/movies
    // @GetMapping("/")  // Carefull, specifying a trailing / here imply you need it in the curl command also
    @GetMapping
    Flux<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping(
            value = "/{id}/events",
            produces= MediaType.TEXT_EVENT_STREAM_VALUE
    )
    Flux<MovieEvent> streamMovieEvents(@PathVariable String id) {
        return movieService.streamMovieEvents(id);
    }
}
