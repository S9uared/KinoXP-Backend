package dat3.kinoxp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder //We will talk about this in the class
@NoArgsConstructor
@Entity
public class Theater {

    @Id
    private int id;

    @Column(name="total_rows", nullable = false)
    private int rows;
    @Column(name="seats_per_row", nullable = false)
    private int seatsPerRow;

    @OneToMany(mappedBy = "theater", cascade = CascadeType.PERSIST, fetch=FetchType.LAZY)
    List<Showing> showings;

    @OneToMany(mappedBy = "theater", cascade = CascadeType.PERSIST, fetch=FetchType.LAZY)
    List<Seat> seats;

    public void addShowing(Showing showing){
        if(showings == null){
            showings = new ArrayList<>();
        }
        showings.add(showing);
    }
    public void addSeat(Seat seat){
        if(seats == null){
            seats = new ArrayList<>();
        }
        seats.add(seat);
    }

    public Theater(int id, int rows, int seatsPerRow)
    {
        this.id = id;
        this.rows = rows;
        this.seatsPerRow = seatsPerRow;
    }
}
