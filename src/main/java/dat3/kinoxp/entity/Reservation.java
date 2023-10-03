package dat3.kinoxp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long showingId;
    private int row;
    private int number;

    public Reservation(Long showingId, int row, int number) {
        this.showingId = showingId;
        this.row = row;
        this.number = number;
    }
}