package dat3.kinoxp.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "movie_name")
    private String movieName;

    @Column(name = "age_restriction")
    private int ageRestriction;

    @Column(name = "category")
    private String category;

    public Movie(String movieName, int ageRestriction, String category) {
        this.movieName = movieName;
        this.ageRestriction = ageRestriction;
        this.category = category;
    }
}
