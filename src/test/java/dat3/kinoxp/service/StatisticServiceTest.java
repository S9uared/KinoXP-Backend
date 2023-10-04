package dat3.kinoxp.service;

import dat3.kinoxp.dto.StatisticRequest;
import dat3.kinoxp.dto.StatisticResponse;
import dat3.kinoxp.entity.Movie;
import dat3.kinoxp.entity.Statistic;
import dat3.kinoxp.repository.MovieRepository;
import dat3.kinoxp.repository.StatisticRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@DataJpaTest
class StatisticServiceTest {


    @Autowired
    StatisticRepository statisticRepository;

    @Autowired
    MovieRepository movieRepository;


    StatisticService statisticService;
    Movie m1, m2;
    Statistic s1, s2, s3;
    LocalDate date;

    @BeforeEach
    void setUp() {
        date = LocalDate.now();
        m1 = movieRepository.save(new Movie("James Bond", 13, "Action"));
        m2 = movieRepository.save(new Movie("Scream", 18, "Horror"));
        s1 = statisticRepository.save(new Statistic(m1, date.minusDays(2), 40));
        s2 = statisticRepository.save(new Statistic(m1, date.minusDays(1), 60));
        s3 = statisticRepository.save(new Statistic(m2, date, 80));
        statisticService = new StatisticService(statisticRepository, movieRepository);
    }

    @Test
    void testAddStatisticSuccess() {
        StatisticRequest request = StatisticRequest.builder().
                movieId(1).
                date(LocalDate.now().minusDays(2)).
                build();
        StatisticResponse response = statisticService.addStatistic(request);
        assertEquals(4, response.getId());
        assertTrue(statisticRepository.existsById(4));
    }

    @Test
    void testAddStatisticFutureDateException() {
        StatisticRequest request = StatisticRequest.builder().
                movieId(1).
                date(LocalDate.now().plusDays(1)).
                build();
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> statisticService.addStatistic(request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    void testAddStatisticMovieNot_FoundException() {
        StatisticRequest request = StatisticRequest.builder().
                movieId(3).
                date(LocalDate.now().minusDays(1)).
                build();
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> statisticService.addStatistic(request));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void testDeleteStatByIdSuccess() {
        statisticService.deleteStatById(2);
        assertFalse(statisticRepository.existsById(2));
    }

    @Test
    void testDeleteStatByIdStatNotFoundException() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> statisticService.deleteStatById(5));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
}