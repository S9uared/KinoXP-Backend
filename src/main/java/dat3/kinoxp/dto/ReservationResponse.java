package dat3.kinoxp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dat3.kinoxp.entity.Reservation;
import dat3.kinoxp.entity.Seat;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)

    public class ReservationResponse {
        private int id;
        private int showingId;
        private CustomerInfoResponse customerInfo;
        private List<SeatResponse> seats;

        public ReservationResponse(Reservation reservation) {
            this.id = reservation.getId();
            this.showingId = reservation.getShowing().getId();
            this.customerInfo = new CustomerInfoResponse(reservation.getCustomerInfo());
            this.seats = reservation.getSeats().stream().map(seat -> new SeatResponse(seat)).toList();
        }
    }