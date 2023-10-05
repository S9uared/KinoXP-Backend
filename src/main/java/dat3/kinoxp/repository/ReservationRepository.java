package dat3.kinoxp.repository;

import dat3.kinoxp.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findAllByShowingId(int showingId);

    boolean existsByShowingIdAndSeatId(int showingId, int seatId);

    List<Reservation> getReservationsByPhoneNumber(String phoneNumber);
}
