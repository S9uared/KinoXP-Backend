package dat3.kinoxp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import dat3.kinoxp.entity.Movie;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieResponse {
    private int id;
    private String movieName;
    private int ageRestriction;
    private String category;
    private LocalDateTime created;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime edited;

    public MovieResponse(Movie movie) {
        this.id = movie.getId();
        this.movieName = movie.getMovieName();
        this.ageRestriction = movie.getAgeRestriction();
        this.category = movie.getCategory();
        this.created = movie.getCreated();
        this.edited = movie.getEdited();
    }
}
