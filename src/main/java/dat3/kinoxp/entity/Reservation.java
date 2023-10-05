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
    @OneToOne
    @JoinColumn
    private SeatId seatId;
    @ManyToOne
    private Showing showing;

    public Reservation(String phoneNumber, SeatId seatId, Showing showing) {
        this.phoneNumber = phoneNumber;
        this.seatId = new SeatId;
        this.showing = showing;
    }
}