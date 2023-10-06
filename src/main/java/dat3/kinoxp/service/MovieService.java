package dat3.kinoxp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import dat3.kinoxp.api_facade.AzureTranslate;
import dat3.kinoxp.api_facade.OmdbFacade;
import dat3.kinoxp.dto.MovieOmdbResponse;
import dat3.kinoxp.entity.Movie;
import dat3.kinoxp.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MovieService {

    MovieRepository movieRepository;
    //AzureTranslate translator;
    OmdbFacade omdbFacade;

    /*
    public MovieService(MovieRepository movieRepository, AzureTranslate translator, OmdbFacade omdbFacade) {

        this.movieRepository = movieRepository;
        this.translator = translator;
        this.omdbFacade = omdbFacade;
    }
    */

    public MovieService(MovieRepository movieRepository, OmdbFacade omdbFacade) {
        this.movieRepository = movieRepository;
        this.omdbFacade = omdbFacade;
    }

    public Movie getMovieByImdbId(String imdbId) {
        return movieRepository.findByImdbID(imdbId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
    }


    public Movie addMovie(String imdbId) throws JsonProcessingException {
        MovieOmdbResponse dto = omdbFacade.getMovie(imdbId);
        //String dkPlot = translator.translate(dto.getPlot());

        Movie movie = Movie.builder()
                .title(dto.getTitle())
                .year(dto.getYear())
                .rated(dto.getRated())
                .released(dto.getReleased())
                .runtime(dto.getRuntime())
                .genre(dto.getGenre())
                .director(dto.getDirector())
                .writer(dto.getWriter())
                .actors(dto.getActors())
                .metascore(dto.getMetascore())
                .imdbRating(dto.getImdbRating())
                .imdbVotes(dto.getImdbVotes())
                .website(dto.getWebsite())
                .response(dto.getResponse())
                .plot(dto.getPlot())
                //.plotDK(dkPlot)
                .poster(dto.getPoster())
                .imdbID(dto.getImdbID())
                .build();
        try {
            movieRepository.save(movie);
            return movie;
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getRootCause().getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not add movie");
        }
    }
}

    /*
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
*/