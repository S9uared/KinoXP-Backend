package dat3.kinoxp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder //We will talk about this in the class
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

    @Column(name = "type", nullable = false)
    private ShowingType type;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Theater theater;

    @OneToMany(mappedBy = "showing")
    List<Reservation> reservations;

    public Showing(LocalDate date, LocalTime time, ShowingType type, Movie movie, Theater theater ){
        this.date = date;
        this.time = time;
        this.type = type;
        this.movie = movie;
        this.theater = theater;
        movie.addShowing(this);
        theater.addShowing(this);
    }

    public void addReservation(Reservation reservation){
        if (reservations == null){
            reservations = new ArrayList<>();
        }
        reservations.add(reservation);
    }
}
