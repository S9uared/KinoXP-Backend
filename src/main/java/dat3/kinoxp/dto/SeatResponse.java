package dat3.kinoxp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dat3.kinoxp.entity.Seat;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SeatResponse
{
    private int id;
    private int theaterId;
    private int rowNumber;
    private int seatNumber;
    private String status;
    private String type;

    public SeatResponse(Seat seat){
        this.id = seat.getId();
        this.theaterId = seat.getTheater().getId();
        this.rowNumber = seat.getRowNumber();
        this.seatNumber = seat.getSeatNumber();
        this.status = seat.getStatus();
        this.type = seat.getType();
    }
}
