package dat3.kinoxp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import dat3.kinoxp.dto.ReservationRequest;
import dat3.kinoxp.dto.ReservationResponse;
import dat3.kinoxp.entity.Reservation;
import dat3.kinoxp.repository.ReservationRepository;
import dat3.kinoxp.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ReservationServiceTest {

  /*  @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
*//*
    @Test
    public void testCreateReservation() {
        Reservation reservationToCreate = new Reservation();
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservationToCreate);

        Reservation createdReservation = reservationService.createReservation(reservationToCreate);
        assertNotNull(createdReservation);
    }
    *//*
    @Test
    public void testEditReservation() {
        int reservationId = 1;
        Reservation existingReservation = new Reservation();
        existingReservation.setId(reservationId);
        ReservationRequest updatedReservation = new ReservationRequest();

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(existingReservation));
        //when(reservationRepository.save(any(Reservation.class))).thenReturn(Optional.of(updatedReservation));

        ReservationResponse editedReservation = reservationService.editReservation(updatedReservation, reservationId);
        assertNotNull(editedReservation);
        assertEquals(reservationId, editedReservation.getId());
    }

    @Test
    public void testViewReservationById() {
        int showingId = 1;
        int reservationId = 2;
        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        reservation.setShowingId(showingId);

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));

        ReservationResponse viewedReservation = reservationService.viewReservationById(reservationId);
        assertNotNull(viewedReservation);
        assertEquals(showingId, viewedReservation.getShowingId());
    }

    @Test
    public void testDeleteReservationReservationNotFound() {
        int showingId = 1;
        int reservationId = 2;

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> reservationService.deleteReservation(reservationId));

        verify(reservationRepository, never()).deleteById(reservationId);
    }

    @Test
    public void testDeleteReservationReservationDoesNotBelongToShow() {
        int showingId = 1;
        int reservationId = 2;

        Reservation reservationToDelete = new Reservation();
        reservationToDelete.setId(reservationId);
        reservationToDelete.setShowingId(3);

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservationToDelete));

        assertThrows(RuntimeException.class, () -> reservationService.deleteReservation(reservationId));

        verify(reservationRepository, never()).deleteById(reservationId);
    }*/
}