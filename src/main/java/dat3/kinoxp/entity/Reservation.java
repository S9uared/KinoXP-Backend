package dat3.kinoxp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.mapping.ToOne;

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
    @ManyToOne
    @JoinColumn
    private Seat seat;
    @ManyToOne
    private Showing showing;

    public Reservation(String phoneNumber, Seat seat, Showing showing) {
        this.phoneNumber = phoneNumber;
        this.seat = Seat;
        this.showing = showing;
    }
}