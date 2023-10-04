package dat3.kinoxp.api;

import dat3.kinoxp.dto.ReservationRequest;
import dat3.kinoxp.dto.ReservationResponse;
import dat3.kinoxp.entity.Reservation;
import dat3.kinoxp.entity.Showing;
import dat3.kinoxp.repository.ReservationRepository;
import dat3.kinoxp.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Reservation>> getReservationsForMovie(@PathVariable int movieId) {
        List<Reservation> reservations = reservationService.getReservationsForMovie(movieId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReservationResponse createReservation(@RequestBody ReservationRequest body){
        return reservationService.createReservation(body);
    }

    /*@PutMapping("/{reservationId}")
    public ReservationResponse editReservation(
            @PathVariable int reservationId, @RequestBody ReservationRequest body) {
        return reservationService.editReservation(body, reservationId);
    }*/

    @GetMapping("/showing/{showingId}")
    public ResponseEntity<List<Reservation>> viewReservationsForShowing(@PathVariable int showingId) {
        List<Reservation> reservations = reservationService.viewReservationsForShowing(showingId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/{showingId}/{reservationId}")
    public ReservationResponse viewReservationById(int reservationId) {
        return reservationService.viewReservationById(reservationId);
    }

    @DeleteMapping("/{reservationId}/{showingId}")
    public void deleteReservation(@PathVariable int reservationId) {
        reservationService.deleteReservation(reservationId);
    }
}