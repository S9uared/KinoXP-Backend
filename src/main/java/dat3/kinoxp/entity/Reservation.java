package dat3.kinoxp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.mapping.ToOne;

import java.util.List;

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

    @ManyToMany
    @JoinColumn
    private List<Seat> seats;

    @ManyToOne
    private Showing showing;
    @ManyToOne
    private CustomerInfo customerInfo;


    public Reservation(Seat seat, Showing showing, CustomerInfo customerInfo) {
        this.seat = seat;
        this.showing = showing;
        this.customerInfo = customerInfo;
        seat.addReservation(this);
        showing.addReservation(this);
        customerInfo.addReservation(this);
    }
}