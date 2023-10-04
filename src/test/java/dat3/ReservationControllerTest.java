package dat3;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import dat3.kinoxp.api.ReservationController;
import dat3.kinoxp.entity.Reservation;
import dat3.kinoxp.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class ReservationControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ReservationController reservationController;

    @Mock
    private ReservationService reservationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();
    }

    @Test
    public void testCreateReservation() throws Exception {
        // Mock the service to return the created reservation
        when(reservationService.createReservation(any(Reservation.class))).thenReturn(new Reservation());

        mockMvc.perform(post("/api/reservations/create")
                        .contentType("application/json")
                        .content("{ \"showingId\": 1, \"row\": 2, \"number\": 3 }"))
                .andExpect(status().isCreated());
    }

    @Test
    public void testEditReservation() throws Exception {
        Long reservationId = 1L;
        Reservation updatedReservation = new Reservation();

        // Mock the service to return the edited reservation
        when(reservationService.editReservation(eq(reservationId), any(Reservation.class))).thenReturn(new Reservation());

        mockMvc.perform(put("/api/reservations/{reservationId}", reservationId)
                        .contentType("application/json")
                        .content("{ \"showingId\": 1, \"row\": 2, \"number\": 3 }"))
                .andExpect(status().isOk());
    }

    @Test
    public void testViewReservationById() throws Exception {
        Long showingId = 1L;
        Long reservationId = 2L;
        Reservation reservation = new Reservation();
        reservation.setShowingId(showingId);

        // Mock the service to return the reservation
        when(reservationService.viewReservationById(eq(showingId), eq(reservationId))).thenReturn(reservation);

        mockMvc.perform(get("/api/reservations/{showingId}/{reservationId}", showingId, reservationId))
                .andExpect(status().isOk());
    }
}