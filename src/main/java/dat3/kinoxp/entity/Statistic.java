package dat3.kinoxp.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Builder //We will talk about this in the class
@NoArgsConstructor
@Entity
public class Statistic{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    Movie movie;

    @Column(name="date", length=15, nullable = false)
    private LocalDate date;

    @Column(name="total_reservations", nullable = false)
    private int totalReservations;

    public Statistic(Movie movie, LocalDate date, int totalReservations) {
        this.movie = movie;
        this.date = date;
        this.totalReservations = totalReservations;
        movie.addStatistic(this);
    }
}
