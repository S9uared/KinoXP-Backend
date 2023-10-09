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
    @JoinTable(name = "seat_reservation",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "seat_id"))
    private List<Seat> seats;

    @ManyToOne
    private Showing showing;
    @ManyToOne
    private CustomerInfo customerInfo;


    public Reservation(Showing showing, CustomerInfo customerInfo) {
        this.showing = showing;
        this.customerInfo = customerInfo;
       // seat.addReservation(this);
        showing.addReservation(this);
        customerInfo.addReservation(this);
    }
}