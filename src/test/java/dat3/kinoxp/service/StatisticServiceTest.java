package dat3.kinoxp.service;

import dat3.kinoxp.entity.Statistic;
import dat3.kinoxp.repository.StatisticRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StatisticServiceTest {


    @Autowired
    StatisticRepository statisticRepository;
    /*
    @Autowired
    MovieRepository movieRepository;
    */

    StatisticService statisticService;
    //Movie m1, m2;
    Statistic s1, s2, s3;

    @BeforeEach
    void setUp() {
        /*
        m1 = movieRepository.save(new Movie("James Bond", 13, "Action");
        m2 = movieRepository.save(new Movie("Scream", 18, "Horror");
        s1 = statisticRepository.save(new Statistic(m1.getId(), date.minusDays(2), 40))
        s2 = statisticRepository.save(new Statistic(m1.getId(), date.minusDays(1), 60))
        s3 = statisticRepository.save(new Statistic(m2.getId(), date, 80))
        */
        statisticService = new StatisticService(statisticRepository); //Maybe add movieRepo in here too? Not sure. Will see when tests can be run
    }

    @Test
    void testAddStatisticSuccess() {

    }
    @Test
    void testAddStatisticFutureDateException() {

    }
    @Test
    void testAddStatisticMovieNot_FoundException() {

    }

    @Test
    void testDeleteStatByIdSuccess() {

    }
    @Test
    void testDeleteStatByIdStatNotFoundException() {

    }
}