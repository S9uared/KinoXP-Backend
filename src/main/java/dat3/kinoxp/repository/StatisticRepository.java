package dat3.kinoxp.repository;

import dat3.kinoxp.entity.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Integer> {
    List<Statistic> getStatisticsByMovieId(int id);
}
