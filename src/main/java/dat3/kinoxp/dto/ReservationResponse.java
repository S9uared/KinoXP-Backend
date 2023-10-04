package dat3.kinoxp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dat3.kinoxp.entity.Reservation;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ReservationResponse {
    private int id;
    private int showingId;
    private String phoneNumber;
    private int row;
    private int number;

    public ReservationResponse(Reservation reservation) {
        this.id = reservation.getId();
        this.showingId = reservation.getShowing().getId();
        this.phoneNumber = reservation.getPhoneNumber();
        this.row = reservation.getRow();
        this.number = reservation.getNumber();
    }
}