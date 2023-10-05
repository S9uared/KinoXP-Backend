package dat3.kinoxp.api;

import dat3.kinoxp.dto.ReservationRequest;
import dat3.kinoxp.dto.ReservationResponse;
import dat3.kinoxp.entity.Reservation;
import dat3.kinoxp.service.ReservationService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    @GetMapping("/showing/{showingId}")
    public List<ReservationResponse> getReservationsForShowing(@PathVariable int showingId) {
        return reservationService.viewReservationsForShowing(showingId);
    }

    @GetMapping("/reservation/{reservationId}")
    public ReservationResponse getReservationById(@PathVariable  int reservationId) {
        return reservationService.viewReservationById(reservationId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReservationResponse createReservation(@RequestBody ReservationRequest body){
        return reservationService.createReservation(body);
    }

    @DeleteMapping("/{reservationId}")
    public void deleteReservation(@PathVariable int reservationId) {
        reservationService.deleteReservation(reservationId);
    }

     /*@PutMapping("/{reservationId}")
    public ReservationResponse editReservation(
            @PathVariable int reservationId, @RequestBody ReservationRequest body) {
        return reservationService.editReservation(body, reservationId);
    }*/
}