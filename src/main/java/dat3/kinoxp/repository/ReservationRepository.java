package dat3.kinoxp.repository;

import dat3.kinoxp.entity.Reservation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {
    List<Reservation> findByShowing_Movie_Id(Long movieId);

    List<Reservation> findByShowingId(Long showingId);
}
