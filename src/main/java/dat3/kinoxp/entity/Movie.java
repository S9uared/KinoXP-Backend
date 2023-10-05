package dat3.kinoxp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    List<Showing> showings;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    List<Statistic> statistics;

    public Movie(String movieName, int ageRestriction, String category) {
        this.movieName = movieName;
        this.ageRestriction = ageRestriction;
        this.category = category;
    }

    public void addShowing(Showing showing){
        if(showings == null){
            showings = new ArrayList<>();
        }
        showings.add(showing);
    }

    public void addStatistic(Statistic statistic){
        if(statistics == null){
            statistics = new ArrayList<>();
        }
        statistics.add(statistic);
    }
}
