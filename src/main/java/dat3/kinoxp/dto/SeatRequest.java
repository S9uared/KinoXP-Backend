package dat3.kinoxp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatRequest {
    private int rowNumber;
    private int seatNumber;
    private String type;
}
