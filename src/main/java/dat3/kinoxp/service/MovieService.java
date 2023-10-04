package dat3.kinoxp.service;

import dat3.kinoxp.dto.MovieRequest;
import dat3.kinoxp.dto.MovieResponse;
import dat3.kinoxp.entity.Movie;
import dat3.kinoxp.repository.MovieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<MovieResponse> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        List<MovieResponse> response = new ArrayList<>();
        for (Movie movie : movies) {
            MovieResponse movieResponse = new MovieResponse(movie);
            response.add(movieResponse);
        }
        return response;
    }

    public MovieResponse getMovieById(int id) {
        Movie movie = findMovieById(id);
        return new MovieResponse(movie);
    }

    public MovieResponse addMovie(MovieRequest movieRequest) {
        Movie movie = new Movie(
                movieRequest.getMovieName(),
                movieRequest.getAgeRestriction(),
                movieRequest.getCategory()
        );
        movie = movieRepository.save(movie);
        return new MovieResponse(movie);
    }

    public ResponseEntity<Void> updateMovie(int id, MovieRequest movieRequest) {
        Movie movie = findMovieById(id);

        // Update movie properties here based on movieRequest

        movieRepository.save(movie);

        return ResponseEntity.ok().build();
    }

    public void deleteMovie(int id) {
        Movie movie = findMovieById(id);
        movieRepository.delete(movie);
    }

    private Movie findMovieById(int id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie with this id does not exist"));
    }
}

