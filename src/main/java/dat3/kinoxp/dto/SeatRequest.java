package dat3.kinoxp.dto;

import dat3.kinoxp.entity.Seat;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatRequest {
    private int theaterId;
    private int rowNumber;
    private int seatNumber;
    private String status;
    private String type;
}
