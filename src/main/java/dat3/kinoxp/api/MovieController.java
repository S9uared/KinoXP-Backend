package dat3.kinoxp.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import dat3.kinoxp.dto.MovieOmdbResponse;
import dat3.kinoxp.entity.Movie;
import dat3.kinoxp.service.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/movies")
public class MovieController {

    MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    //Security -> Anonymous
    @GetMapping
    public List<MovieOmdbResponse> getMovies() {
        return movieService.getAllMovies();
    }

    //Security -> Anonymous
    @GetMapping("/{id}")
    public MovieOmdbResponse getById(@PathVariable int id){
        return movieService.getMovieById(id);
    }

    // Security -> Anonymous
    @GetMapping("/imdbid/{imdbId}")
    public MovieOmdbResponse getMovie(@PathVariable String imdbId) {
        return movieService.getMovieByImdbId(imdbId);
    }

    //Security -> ADMIN
    @PostMapping("/{imdbId}")
    public Movie addMovie(@PathVariable String imdbId) throws JsonProcessingException {
        return movieService.addMovie(imdbId);
    }
}