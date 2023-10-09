package dat3.kinoxp.repository;

import dat3.kinoxp.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    Optional<Movie> findByImdbID(String imdbId);
}