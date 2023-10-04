package dat3.kinoxp.repository;

import dat3.kinoxp.entity.Movie;
import dat3.kinoxp.entity.Statistic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class StatisticRepositoryTest {

    @Autowired
    StatisticRepository statisticRepository;

    @Autowired
    MovieRepository movieRepository;

    Movie m1, m2;

    LocalDate date = LocalDate.now();

    @BeforeEach
    void setUp(){
        m1 = movieRepository.save(new Movie("James Bond", 13, "Action"));
        m2 = movieRepository.save(new Movie("Scream", 18, "Horror"));
        statisticRepository.save(new Statistic(m1, date.minusDays(2), 40));
        statisticRepository.save(new Statistic(m1, date.minusDays(1), 60));
    }

    @Test
    void testStatisticMovieIdSearchSuccess(){
        List<Statistic> statList = statisticRepository.getStatisticsByMovieId(m1.getId());
        assertEquals(2, statList.size());
    }
}