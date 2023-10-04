package dat3.kinoxp.configuration;

import dat3.kinoxp.entity.Movie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MovieTestDataFactory {
    private static final List<String> movieNames = Arrays.asList(
            "Movie1", "Movie2", "Movie3", "Movie4", "Movie5",
            "Movie6", "Movie7", "Movie8", "Movie9", "Movie10"
    );

    private static final List<Integer> ageRestrictions = Arrays.asList(0, 6, 12, 16, 18);

    private static final List<String> categories = Arrays.asList(
            "Action", "Comedy", "Drama", "Science Fiction", "Horror"
    );

    public static List<Movie> generateTestMovies() {
        Random random = new Random();
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            String movieName = movieNames.get(random.nextInt(movieNames.size()));
            int ageRestriction = ageRestrictions.get(random.nextInt(ageRestrictions.size()));
            String category = categories.get(random.nextInt(categories.size()));
            Movie movie = new Movie(movieName, ageRestriction, category);
            movies.add(movie);
        }
        return movies;
    }
}
