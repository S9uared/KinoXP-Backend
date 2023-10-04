package dat3.kinoxp.repository;

import dat3.kinoxp.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findByShowingId(int showingId);

    boolean existsByShowingIdAndNumberAndRow(int showingId, int number, int row);

    List<Reservation> getReservationsByPhoneNumber(String phoneNumber);
}
