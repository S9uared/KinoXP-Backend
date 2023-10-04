package dat3.kinoxp.service;

import dat3.kinoxp.dto.ReservationRequest;
import dat3.kinoxp.dto.ReservationResponse;
import dat3.kinoxp.entity.Reservation;
import dat3.kinoxp.entity.Showing;
import dat3.kinoxp.repository.ReservationRepository;
import dat3.kinoxp.repository.ShowingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private ReservationRepository reservationRepository;
    private ShowingRepository showingRepository;

    public ReservationService(ReservationRepository reservationRepository, ShowingRepository showingRepository){
        this.reservationRepository = reservationRepository;
        this.showingRepository = showingRepository;
    }

    // Skal den tjekke at der ikke allerede eksistere en lignende reservation
    public ReservationResponse createReservation(ReservationRequest body) {
        if(!showingRepository.existsById(body.getShowingId())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Showing with that id does not exist");
        }
        if(!reservationRepository.existsByShowingIdAndNumberAndRow(body.getShowingId(), body.getNumber(), body.getRow())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Already reserved, buddy :)");
        }
        Showing showing = showingRepository.findById(body.getShowingId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Showing with this id does not exist"));

        Reservation reservation = reservationRepository.save(new Reservation(body.getPhoneNumber(), body.getRow(), body.getNumber(), showing));
        return new ReservationResponse(reservation);
    }

   /* public ReservationResponse editReservation(ReservationRequest body, int reservationId) {
        Reservation reservation = getReservationById(reservationId);

        reservation.setShowingId(body.getShowing());
        reservation.setRow(body.getRow());
        reservation.setNumber(body.getNumber());

        reservation = reservationRepository.save(reservation);
        return new ReservationResponse(reservation);

    }*/

    public List<ReservationResponse> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream().map(reservation -> new ReservationResponse(reservation)).toList();
    }

    public ReservationResponse viewReservationById(int reservationId) {
        Reservation reservation = getReservationById(reservationId);

        return new ReservationResponse(reservation);
    }

    public List<ReservationResponse> getReservationsByPhoneNumber(String phoneNumber){
        List<Reservation> reservations = reservationRepository.getReservationsByPhoneNumber(phoneNumber);
        return reservations.stream().map(reservation -> new ReservationResponse(reservation)).toList();
    }

    public List<Reservation> getReservationsForMovie(int movieId) {
        List<Showing> showings = showingRepository.getShowingsByMovieId(movieId);
        return showings.stream().map(showing -> {
            return reservationRepository.findByShowingId(showing.getId());
        }).toList().stream().flatMap(List::stream).toList();
    }

    public List<Reservation> viewReservationsForShowing(int showingId) {
        return reservationRepository.findByShowingId(showingId);
    }

    private Reservation getReservationById(int reservationId){
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation with this id does not exist"));
    }

    public void deleteReservation(int reservationId) {
        Reservation reservation = getReservationById(reservationId);
        reservationRepository.delete(reservation);
    }
}