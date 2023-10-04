package dat3.kinoxp.dto;

import dat3.kinoxp.entity.Movie;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieRequest {

    private String movieName;
    private int ageRestriction;
    private String category;

    public Movie getMovieEntity(MovieRequest movieRequest) {
        return new Movie(
                movieRequest.getMovieName(),
                movieRequest.getAgeRestriction(),
                movieRequest.getCategory()
        );
    }

    public MovieRequest(Movie movie) {
        this.movieName = movie.getMovieName();
        this.ageRestriction = movie.getAgeRestriction();
        this.category = movie.getCategory();
    }
}
