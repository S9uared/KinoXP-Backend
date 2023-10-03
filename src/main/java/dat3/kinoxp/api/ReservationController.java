package dat3.kinoxp.api;

import dat3.kinoxp.entity.Reservation;
import dat3.kinoxp.repository.ReservationRepository;
import dat3.kinoxp.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Reservation>> getReservationsForMovie(@PathVariable Long movieId) {
        List<Reservation> reservations = reservationService.getReservationsForMovie(movieId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        // Assuming you have a method in your service to retrieve a Showing by criteria
        // This is a simplified example; adjust it to your application's logic
        Showing showing = showingService.findShowingByCriteria(reservation.getMovieId(), reservation.getDate(), reservation.getTime());

        if (showing != null) {
            reservation.setShowingId(showing.getId());
            Reservation createdReservation = reservationService.createReservation(reservation);
            return new ResponseEntity<>(createdReservation, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{reservationId}")
    public ResponseEntity<Reservation> editReservation(
            @PathVariable Long reservationId, @RequestBody Reservation updatedReservation) {
        Reservation editedReservation = reservationService.editReservation(reservationId, updatedReservation);
        return new ResponseEntity<>(editedReservation, HttpStatus.OK);
    }

    @GetMapping("/showing/{showingId}")
    public ResponseEntity<List<Reservation>> viewReservationsForShowing(@PathVariable Long showingId) {
        List<Reservation> reservations = reservationService.viewReservationsForShowing(showingId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/{showingId}/{reservationId}")
    public Reservation viewReservationById(Long showingId, Long reservationId) {
        Reservation reservation = ReservationService.viewReservationById(showingId, reservationId);

        // Check if the reservation exists and if its showingId matches the provided showingId
        if (reservation != null && reservation.getShowingId().equals(showingId)) {
            return reservation;
        } else {
            return null; // Reservation not found or showing mismatch
        }
    }

    @DeleteMapping("/{reservationId}/{showingId}")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable Long reservationId, @PathVariable Long showingId) {
        reservationService.deleteReservation(reservationId, showingId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}