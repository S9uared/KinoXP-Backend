package dat3.kinoxp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationDTO {
    private Long id;
    private Long showingId;
    private int row;
    private int number;
}