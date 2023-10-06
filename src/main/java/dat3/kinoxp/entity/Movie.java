package dat3.kinoxp.entity;

import dat3.kinoxp.entity.Showing;
import dat3.kinoxp.entity.Statistic;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String year;
    private String rated;
    private String released;
    private String runtime;
    private String genre;
    private String director;
    private String writer;
    private String actors;

    @Column(length = 2000)
    private String plot;
    @Column(length = 2000)
    private String plotDK;
    private String poster;

    private String metascore;
    private String imdbRating;
    private String imdbVotes;

    @Column(unique = true)
    private String imdbID;
    private String website;
    private String response;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    List<Showing> showings;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    List<Statistic> statistics;

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
    /*
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
    */