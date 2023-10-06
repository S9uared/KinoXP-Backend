package dat3.kinoxp.configuration;

import dat3.kinoxp.entity.Movie;
import dat3.kinoxp.dto.MovieOmdbRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MovieTestDataFactory {
    private static final List<String> movieTitles = Arrays.asList(
            "Movie1", "Movie2", "Movie3", "Movie4", "Movie5",
            "Movie6", "Movie7", "Movie8", "Movie9", "Movie10"
    );

    private static final List<Integer> years = Arrays.asList(2000, 2005, 2010, 2015, 2020);

    private static final List<String> genres = Arrays.asList(
            "Action", "Comedy", "Drama", "Science Fiction", "Horror"
    );

    public static List<MovieOmdbRequest> generateTestMovies() {
        Random random = new Random();
        List<MovieOmdbRequest> movies = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            String title = movieTitles.get(random.nextInt(movieTitles.size()));
            String year = String.valueOf(years.get(random.nextInt(years.size())));
            String genre = genres.get(random.nextInt(genres.size()));

            MovieOmdbRequest movie = new MovieOmdbRequest();
            movie.setTitle(title);
            movie.setYear(year);
            movie.setGenre(genre);

            // Set other fields of the MovieOmdbRequest as needed

            movies.add(movie);
        }
        return movies;
    }
}
