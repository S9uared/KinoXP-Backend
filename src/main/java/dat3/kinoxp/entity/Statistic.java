package dat3.kinoxp.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String date;

    @Column(name="weekly_reservations", nullable = false)
    private int totalReservations;

}
