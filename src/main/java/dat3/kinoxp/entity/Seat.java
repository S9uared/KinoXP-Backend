package dat3.kinoxp.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder //We will talk about this in the class
@NoArgsConstructor
@Entity
public class Seat
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    Theater theater;

    @Column(name="row_number", nullable=false)
    private int rowNumber;

    @Column(name="seat_number", nullable=false)
    private int seatNumber;

    @Column(name="status", nullable=false)
    private String status;

    @Column(name="type", nullable = false)
    private String type;


    public Seat(Theater theater, int rowNumber, int seatNumber, String status, String type)
    {
        this.theater = theater;
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
        this.status = status;
        this.type = type;
        theater.addSeat(this);
    }
}
