package dat3.kinoxp.service;

import dat3.kinoxp.dto.CustomerInfoRequest;
import dat3.kinoxp.dto.ReservationRequest;
import dat3.kinoxp.dto.ReservationResponse;
import dat3.kinoxp.entity.CustomerInfo;
import dat3.kinoxp.entity.Reservation;
import dat3.kinoxp.entity.Seat;
import dat3.kinoxp.entity.Showing;
import dat3.kinoxp.repository.CustomerInfoRepository;
import dat3.kinoxp.repository.ReservationRepository;
import dat3.kinoxp.repository.SeatRepository;
import dat3.kinoxp.repository.ShowingRepository;
import dat3.kinoxp.repository.SeatRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ReservationService {

    private ReservationRepository reservationRepository;
    private ShowingRepository showingRepository;
    private SeatRepository seatRepository;
    private CustomerInfoRepository customerInfoRepository;

    public ReservationService(ReservationRepository reservationRepository, ShowingRepository showingRepository, CustomerInfoRepository customerInfoRepository, SeatRepository seatRepository){
        this.reservationRepository = reservationRepository;
        this.showingRepository = showingRepository;
        this.seatRepository = seatRepository;
        this.customerInfoRepository = customerInfoRepository;
    }

    // Skal den tjekke at der ikke allerede eksistere en lignende reservation
    public ReservationResponse createReservation(ReservationRequest body) {
        if(!showingRepository.existsById(body.getShowingId())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Showing with that id does not exist");
        }
        if(reservationRepository.existsByShowingIdAndSeatId(body.getShowingId(), body.getSeatId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Already reserved, buddy :)");
        }
        if (!customerInfoRepository.existsByPhoneNumber(body.getPhoneNumber())){
            customerInfoRepository.save(new CustomerInfo(body.getPhoneNumber(), body.getFirstName(), body.getLastName(), body.getEmail()));
        }
        Seat seat = seatRepository.findById(body.getSeatId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seat with this id does not exist"));
        CustomerInfo info = customerInfoRepository.findCustomerInfoByPhoneNumber(body.getPhoneNumber());
        Showing showing = showingRepository.findById(body.getShowingId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Showing with this id does not exist"));

        Reservation reservation = reservationRepository.save(new Reservation(seat, showing, info));
        return new ReservationResponse(reservation);
    }

   /* public ReservationResponse editReservation(ReservationRequest body, int reservationId) {
        Reservation reservation = getReservationById(reservationId);

        reservation.setShowingId(body.getShowing());
        reservation.setSeatId(body.getSeat());

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
        List<Reservation> reservations = reservationRepository.getReservationsByCustomerInfoPhoneNumber(phoneNumber);
        return reservations.stream().map(reservation -> new ReservationResponse(reservation)).toList();
    }

    public List<Reservation> getReservationsForMovie(int movieId) {
        List<Showing> showings = showingRepository.getShowingsByMovieId(movieId);
        return showings.stream().map(showing -> {
            return reservationRepository.findAllByShowingId(showing.getId());
        }).toList().stream().flatMap(List::stream).toList();
    }

    public List<ReservationResponse> viewReservationsForShowing(int showingId) {
        List<Reservation> reservations = reservationRepository.findAllByShowingId(showingId);
        return reservations.stream().map(reservation -> new ReservationResponse(reservation)).toList();
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