package dat3.kinoxp.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "seat_row")
    private int row;
    @Column(name = "seat_number")
    private int number;
    @ManyToOne
    private Showing showing;

    public Reservation(String phoneNumber, int row, int number, Showing showing) {
        this.phoneNumber = phoneNumber;
        this.row = row;
        this.number = number;
        this.showing = showing;
    }
}