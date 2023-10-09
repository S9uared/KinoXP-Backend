package dat3.kinoxp.repository;

import dat3.kinoxp.entity.Reservation;
import dat3.kinoxp.entity.Seat;
import dat3.kinoxp.entity.Showing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findAllByShowingId(int showingId);

    List<Reservation> getReservationsByCustomerInfoPhoneNumber(String phoneNumber);

    @Query("SELECT COUNT(r) > 0 FROM Reservation r WHERE :seat MEMBER OF r.seats AND r.showing = :showing")
    boolean existsBySeatAndShowing(@Param("seat") Seat seat, @Param("showing") Showing showing);
}
