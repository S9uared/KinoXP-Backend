package dat3.kinoxp.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import dat3.kinoxp.entity.Movie;
import dat3.kinoxp.service.MovieService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/movies")
public class MovieController {

    MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @RequestMapping("/imdbid/{imdbId}")
    public Movie getMovie(@PathVariable String imdbId) {
        return movieService.getMovieByImdbId(imdbId);
    }

    @PostMapping("/{imdbId}")
    public Movie addMovie(@PathVariable String imdbId) throws JsonProcessingException {
        return movieService.addMovie(imdbId);
    }
}
/*
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<MovieResponse> getMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping(path = "/{id}")
    public MovieResponse getMovieById(@PathVariable int id) {
        return movieService.getMovieById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public MovieResponse addMovie(@RequestBody MovieRequest body) {
        return movieService.addMovie(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editMovie(@RequestBody MovieRequest body, @PathVariable int id) {
        return movieService.updateMovie(id, body);
    }

    @DeleteMapping("/{id}")
    public void deleteMovieById(@PathVariable int id) {
        movieService.deleteMovie(id);
    }
} */

