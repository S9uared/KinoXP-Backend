package dat3.kinoxp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.mapping.ToOne;

import java.util.List;
import java.util.stream.Collectors;

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


    public Reservation(Showing showing, CustomerInfo customerInfo/*, List<Seat> seats*/) {
        this.showing = showing;
        this.customerInfo = customerInfo;
        showing.addReservation(this);
        customerInfo.addReservation(this);

    }


    public String toString(Movie movie) {
        String seatString = seats.stream().map(seat -> "Row "+seat.getRowNumber() +" "+ "Seat "+seat.getSeatNumber()+"\n").collect(Collectors.joining());
        String email = "Hello " + customerInfo.getFirstName() + " " + getCustomerInfo().getLastName()
                +"\n\n"+ "Thank you for your reservation to see "+
                movie.getTitle() + " the " + showing.getDate() +" at KinoXP.\n\n"+
                "You have reserved the following seats: "+"\n\n"+ seatString + "\n\n"+
                "Kind regards\n"+"The Staff of KinoXP";


        return email;
    }
}