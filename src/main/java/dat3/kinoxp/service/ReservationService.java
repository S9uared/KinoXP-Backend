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
    private static ReservationRepository reservationRepository;

    // Create a reservation
    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    // Edit a reservation
    public Reservation editReservation(Long reservationId, Reservation updatedReservation) {
        Optional<Reservation> existingReservationOptional = reservationRepository.findById(reservationId);
        if (existingReservationOptional.isPresent()) {
            Reservation existingReservation = existingReservationOptional.get();
            // Update fields
            existingReservation.setShowingId(updatedReservation.getShowingId());
            existingReservation.setRow(updatedReservation.getRow());
            existingReservation.setNumber(updatedReservation.getNumber());
            return reservationRepository.save(existingReservation);
        } else {
            throw new RuntimeException("Reservation not found with id: " + reservationId);
        }
    }

    // View reservations
    public List<Reservation> getAllReservations() {
        return (List<Reservation>) reservationRepository.findAll();
    }

    // View a reservation by id
    public static Reservation viewReservationById(Long showingId, Long reservationId) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(reservationId);
        if (reservationOptional.isPresent()) {
            Reservation reservation = reservationOptional.get();
            if (reservation.getShowingId().equals(showingId)) {
                return reservation;
            } else {
                throw new RuntimeException("Reservation does not belong to the specified showing");
            }
        } else {
            throw new RuntimeException("Reservation not found with id: " + reservationId);
        }
    }

    // Delete a reservation
    public void deleteReservation(Long showingId, Long reservationId) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(reservationId);
        if (reservationOptional.isPresent()) {
            Reservation reservation = reservationOptional.get();
            if (reservation.getShowingId().equals(showingId)) {
                reservationRepository.deleteById(reservationId);
            } else {
                throw new RuntimeException("Reservation does not belong to the specified showing");
            }
        } else {
            throw new RuntimeException("Reservation not found with id: " + reservationId);
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