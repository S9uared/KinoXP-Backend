package dat3.kinoxp.api;

import dat3.kinoxp.dto.ReservationRequest;
import dat3.kinoxp.dto.ReservationResponse;
import dat3.kinoxp.service.ReservationService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    //Security -> USER
    @GetMapping
    public List<ReservationResponse> getReservations(){
        return reservationService.getAllReservations();
    }

    //Security -> USER
    @GetMapping("/{phoneNumber}")
    public List<ReservationResponse> getReservationsByPhoneNumber(@PathVariable String phoneNumber) throws IOException {
        return reservationService.getReservationsByPhoneNumber(phoneNumber);
    }

    //Security -> USER
    @GetMapping("/showing/{showingId}")
    public List<ReservationResponse> getReservationsForShowing(@PathVariable int showingId) {
        return reservationService.viewReservationsForShowing(showingId);
    }

    //Security -> USER
    @GetMapping("/reservation/{reservationId}")
    public ReservationResponse getReservationById(@PathVariable  int reservationId) throws IOException {
        return reservationService.viewReservationById(reservationId);
    }

    //Security -> Anonymous
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReservationResponse createReservation(@RequestBody ReservationRequest body) throws IOException {
        return reservationService.createReservation(body);
    }

    //Security -> USER
    @DeleteMapping("/{reservationId}")
    public void deleteReservation(@PathVariable int reservationId) {
        reservationService.deleteReservation(reservationId);
    }
}