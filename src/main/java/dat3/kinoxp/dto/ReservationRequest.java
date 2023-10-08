package dat3.kinoxp.dto;

import dat3.kinoxp.entity.Reservation;
import dat3.kinoxp.entity.Showing;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ReservationRequest {
    //private int id;
    private String phoneNumber;
    private int seatId;
    private int showingId;
    private String firstName;
    private String lastName;
    private String email;

}