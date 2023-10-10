package dat3.kinoxp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import dat3.kinoxp.entity.Movie;
import dat3.kinoxp.entity.Showing;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieOmdbResponse {

    private int id;

    @JsonProperty("Title")
    private String title;

    @JsonProperty("Year")
    private String year;

    @JsonProperty("Rated")
    private String rated;

    @JsonProperty("Released")
    private String released;

    @JsonProperty("Runtime")
    private String runtime;

    @JsonProperty("Genre")
    private String genre;

    @JsonProperty("Director")
    private String director;

    @JsonProperty("Writer")
    private String writer;

    @JsonProperty("Actors")
    private String actors;

    @JsonProperty("Plot")
    private String plot;

    @JsonProperty("Language")
    private String language;

    @JsonProperty("Country")
    private String country;

    @JsonProperty("Awards")
    private String awards;

    @JsonProperty("Poster")
    private String poster;

    @JsonProperty("Ratings")
    private List<Rating> ratings;

    @JsonProperty("Metascore")
    private String metascore;

    @JsonProperty("imdbRating")
    private String imdbRating;

    @JsonProperty("imdbVotes")
    private String imdbVotes;

    @JsonProperty("imdbID")
    private String imdbID;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("DVD")
    private String dvd;

    @JsonProperty("BoxOffice")
    private String boxOffice;

    @JsonProperty("Production")
    private String production;

    @JsonProperty("Website")
    private String website;

    @JsonProperty("Response")
    private String response;


    public MovieOmdbResponse(Movie movie, boolean forShowing) {

        this.id = movie.getId();
        this.title = movie.getTitle();
        this.year = movie.getYear();
        this.released = movie.getReleased();
        this.runtime = movie.getRuntime();
        this.genre = movie.getGenre();
        this.director = movie.getDirector();
        this.poster = movie.getPoster();
        this.plot = movie.getPlot();

        if (!forShowing) {
            this.writer = movie.getWriter();
            this.actors = movie.getActors();
            this.metascore = movie.getMetascore();
            this.imdbVotes = movie.getImdbVotes();
            this.website = movie.getWebsite();
            this.response = movie.getResponse();
            this.imdbID = movie.getImdbID();
            this.imdbRating = movie.getImdbRating();
            this.rated = movie.getRated();
        }
    }

    @Getter @Setter
    public static class Rating {

        @JsonProperty("Source")
        private String source;

        @JsonProperty("Value")
        private String value;
    }
}