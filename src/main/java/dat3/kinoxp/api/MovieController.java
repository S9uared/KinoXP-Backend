package dat3.kinoxp.api;

import dat3.kinoxp.dto.MovieRequest;
import dat3.kinoxp.dto.MovieResponse;
import dat3.kinoxp.service.MovieService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/movies")
public class MovieController {

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
}

