package dat3.kinoxp.service;

import dat3.kinoxp.entity.Reservation;
import dat3.kinoxp.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    // Implement methods for creating, editing, viewing, and deleting reservations
    // Create a reservation
    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    // Edit a reservation
    public Reservation editReservation(Long id, Reservation updatedReservation) {
        Optional<Reservation> existingReservationOptional = reservationRepository.findById(id);
        if (existingReservationOptional.isPresent()) {
            Reservation existingReservation = existingReservationOptional.get();
            // Update fields
            existingReservation.setShowingId(updatedReservation.getShowingId());
            existingReservation.setRow(updatedReservation.getRow());
            existingReservation.setNumber(updatedReservation.getNumber());
            return reservationRepository.save(existingReservation);
        } else {
            throw new RuntimeException("Reservation not found with id: " + id);
        }
    }

    // View a reservation
    public List<Reservation> viewReservation() {
        return (List<Reservation>) reservationRepository.findAll();
    }

    // View a reservation by id
    public Reservation viewReservationById(Long reservationId) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(reservationId);
        return reservationOptional.orElse(null);
    }

    // Delete a reservation
    public void deleteReservation(Long id, Long showingId) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);
        if (reservationOptional.isPresent()) {
            reservationRepository.deleteById(id);
        } else {
            throw new RuntimeException("Reservation not found with id: " + id);
        }
    }

    // Get reservations for a movie
    public List<Reservation> getReservationsForMovie(Long movieId) {
        return reservationRepository.findByShowing_Movie_Id(movieId);
    }

    public List<Reservation> viewReservationsForShowing(Long showingId) {
        return reservationRepository.findByShowingId(showingId);
    }

}