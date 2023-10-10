package dat3.kinoxp.repository;

import dat3.kinoxp.entity.Showing;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShowingRepository extends JpaRepository<Showing, Integer> {
    List<Showing> getShowingsByDate(LocalDate date);

    List<Showing> getShowingsByDateAndTheaterId(LocalDate date, int theaterId);
    List<Showing> getShowingsByMovieId(int movieId);
    List<Showing> getShowingsByMovieGenre(String genre);
    boolean existsByTheaterId(int id);

    @Query("SELECT s FROM Showing s WHERE s.date >= :currentDate ORDER BY s.date ASC, s.time ASC")
    List<Showing> findFutureShowingsOrderByDateAndTime(@Param("currentDate") LocalDate currentDate);
}
