package dat3.kinoxp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class MovieOmdbRequest {

    private String title;
    private String year;
    private String rated;
    private String released;
    private String runtime;
    private String genre;
    private String director;
    private String writer;
    private String actors;
    private String plot;
    private String language;
    private String country;
    private String awards;
    private String poster;
    private List<MovieOmdbResponse.Rating> ratings;
    private String metascore;
    private String imdbRating;
    private String imdbVotes;
    private String imdbID;
    private String type;
    private String dvd;
    private String boxOffice;
    private String production;
    private String website;
    private String response;


}

/*
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
}*/
