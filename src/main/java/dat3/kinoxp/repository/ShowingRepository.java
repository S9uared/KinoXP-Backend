package dat3.kinoxp.repository;

import dat3.kinoxp.entity.Showing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ShowingRepository extends JpaRepository<Showing, Integer> {
    List<Showing> getShowingsByDate(LocalDate date);

    List<Showing> getShowingsByMovieId(int movieId);

    List<Showing> getShowingsByMovieCategory(String category);

}
