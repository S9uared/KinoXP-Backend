package dat3.kinoxp.service;

import dat3.kinoxp.dto.StatisticRequest;
import dat3.kinoxp.dto.StatisticResponse;
import dat3.kinoxp.entity.*;
import dat3.kinoxp.repository.*;
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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@DataJpaTest
class StatisticServiceTest {
/*

    @Autowired
    StatisticRepository statisticRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    TheaterRepository theaterRepository;

    @Autowired
    ShowingRepository showingRepository;

    @Autowired
    ReservationRepository reservationRepository;
    StatisticService statisticService;
    Movie m1, m2;
    Statistic s1, s2, s3;
    Theater t1, t2;
    Showing sh1, sh2;
    Reservation r2, r3, r4, r5;
    LocalDate date;

    @BeforeEach
    void setUp() {
        date = LocalDate.now();
        t1 = new Theater(1, 10, 10);
        t2 = new Theater(2, 15, 5);
        theaterRepository.save(t1);
        theaterRepository.save(t2);
        m1 = movieRepository.save(new Movie("James Bond", "13", "105 minutes", "Action"));
        m2 = movieRepository.save(new Movie("Scream", "18","90 minutes", "Horror"));
        s1 = statisticRepository.save(new Statistic(m1, date.minusDays(2), 40));
        s2 = statisticRepository.save(new Statistic(m1, date.minusDays(1), 60));
        s3 = statisticRepository.save(new Statistic(m2, date, 80));
        sh1 = showingRepository.save(new Showing(date, LocalTime.of(16, 30), m1, t1));
        sh2 = showingRepository.save(new Showing(date, LocalTime.of(19, 30), m1, t2));
        List<Reservation> rList = new ArrayList<>();
        for(int i = 0; i < 150; i++){
            rList.add(new Reservation("20202020", i, 2+i, sh1));
        }
        reservationRepository.saveAll(rList);

        r2 = reservationRepository.save(new Reservation("20202020", 10, 14, sh2));
        r3 = reservationRepository.save(new Reservation("36363636", 10, 15, sh2));
        r4 = reservationRepository.save(new Reservation("112", 10, 16,sh2));
        r5 = reservationRepository.save(new Reservation("911", 19, 15,sh2));
        statisticService = new StatisticService(statisticRepository, movieRepository, theaterRepository, showingRepository, reservationRepository);


    }

    @Test
    void testAddStatisticSuccess() {
        StatisticRequest request = StatisticRequest.builder().
                movieId(1).
                date(LocalDate.now()).
                build();

        StatisticResponse response = statisticService.addStatistic(request);
        //154 total reservations above, split on 2 showings. Therefor 77% average
        assertEquals(77, response.getTotalReservations());
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

 */
}