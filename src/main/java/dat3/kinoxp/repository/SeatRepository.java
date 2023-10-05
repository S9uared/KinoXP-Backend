package dat3.kinoxp.repository;

import dat3.kinoxp.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {
    List<Seat> getSeatsByTheaterId(int id);
    boolean existsByTheaterIdAndRowNumberAndSeatNumber(int theaterId, int rowNumber, int seatNumber);
    List<Seat> getSeatsByTheaterIdAndAndType(int id, String type);
}
