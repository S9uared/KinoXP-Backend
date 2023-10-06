package dat3.kinoxp.repository;

import dat3.kinoxp.entity.Showing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShowingRepository extends JpaRepository<Showing, Integer> {
    List<Showing> getShowingsByDate(LocalDate date);
    List<Showing> getShowingsByMovieId(int movieId);
    List<Showing> getShowingsByMovieCategory(String category);
    boolean existsByTheaterId(int id);
}
