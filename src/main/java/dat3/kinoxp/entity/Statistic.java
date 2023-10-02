package dat3.kinoxp.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Statistic{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //Implement after merge with Movie branch
    /*
    @ManyToOne
    Movie movie;
    */

    @Column(name="date", length=15, nullable = false)
    private LocalDate date;

    @Column(name="total_reservations", nullable = false)
    private int totalReservations;

    public Statistic(/*Movie movie,*/ LocalDate date, int totalReservations) {
        //this.movie = movie;
        this.date = date;
        this.totalReservations = totalReservations;
    }
}
