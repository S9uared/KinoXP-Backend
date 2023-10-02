package dat3.kinoxp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Showing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "time", nullable = false)
    private LocalTime time;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Theater theater;

    public Showing(LocalDate date, LocalTime time, Movie movie, Theater theater ){
        this.date = date;
        this.time = time;
        this.movie = movie;
        this.theater = theater;
        movie.addShowing(this);
        theater.addShowing(this);
    }
}
