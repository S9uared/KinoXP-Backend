package dat3;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import dat3.kinoxp.entity.Reservation;
import dat3.kinoxp.repository.ReservationRepository;
import dat3.kinoxp.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateReservation() {
        Reservation reservationToCreate = new Reservation();
        // Mock the repository to return the saved reservation
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservationToCreate);

        Reservation createdReservation = reservationService.createReservation(reservationToCreate);
        assertNotNull(createdReservation);
    }

    @Test
    public void testEditReservation() {
        Long reservationId = 1L;
        Reservation existingReservation = new Reservation();
        existingReservation.setId(reservationId);
        Reservation updatedReservation = new Reservation();

        // Mock the repository to return the existing reservation and save the updated reservation
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(existingReservation));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(updatedReservation);

        Reservation editedReservation = reservationService.editReservation(reservationId, updatedReservation);
        assertNotNull(editedReservation);
        assertEquals(reservationId, editedReservation.getId());
    }

    @Test
    public void testViewReservationById() {
        Long showingId = 1L;
        Long reservationId = 2L;
        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        reservation.setShowingId(showingId);

        // Mock the repository to return the reservation
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));

        Reservation viewedReservation = reservationService.viewReservationById(showingId, reservationId);
        assertNotNull(viewedReservation);
        assertEquals(showingId, viewedReservation.getShowingId());
    }

    @Test
    public void testDeleteReservationReservationNotFound() {
        Long showingId = 1L;
        Long reservationId = 2L;

        // Mock the repository to return an empty Optional, indicating that the reservation doesn't exist
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> reservationService.deleteReservation(reservationId, showingId));

        // Verify that the repository's deleteById method was not called
        verify(reservationRepository, never()).deleteById(reservationId);
    }

    @Test
    public void testDeleteReservationReservationDoesNotBelongToShow() {
        Long showingId = 1L;
        Long reservationId = 2L;

        Reservation reservationToDelete = new Reservation();
        reservationToDelete.setId(reservationId);
        reservationToDelete.setShowingId(3L); // Different showing ID

        // Mock the repository to return the reservation for the given ID
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservationToDelete));

        // Call the service method to delete the reservation
        // The service should throw an exception because the reservation doesn't belong to the specified showing
        assertThrows(RuntimeException.class, () -> reservationService.deleteReservation(reservationId, showingId));

        // Verify that the repository's deleteById method was not called
        verify(reservationRepository, never()).deleteById(reservationId);
    }
}
