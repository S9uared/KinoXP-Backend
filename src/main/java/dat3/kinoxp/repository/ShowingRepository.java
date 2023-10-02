package dat3.kinoxp.repository;

import dat3.kinoxp.entity.Showing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ShowingRepository extends JpaRepository<Showing, Integer> {
    List<Showing> getShowingsByDate(LocalDate date);

    List<Showing> getShowingsByDateAndTime(LocalDate date, LocalTime time);

    List<Showing>  getShowingsByMovie(Movie movie);

    @Query("SELECT s FROM Showing s WHERE s.movie IN (SELECT m.id FROM Movie m WHERE m.genre = :genre)")
    List<Showing> getShowingsByMovieGenre(@Param("genre") String genre);
}
