package dat3.kinoxp.service;

import dat3.kinoxp.dto.ShowingRequest;
import dat3.kinoxp.dto.ShowingResponse;
import dat3.kinoxp.entity.Movie;
import dat3.kinoxp.entity.Showing;
import dat3.kinoxp.entity.ShowingType;
import dat3.kinoxp.entity.Theater;
import dat3.kinoxp.repository.MovieRepository;
import dat3.kinoxp.repository.ShowingRepository;
import dat3.kinoxp.repository.TheaterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DataJpaTest
class ShowingServiceH2Test {

    @Autowired
    ShowingRepository showingRepository;
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    TheaterRepository theaterRepository;

    ShowingService service;

    private Showing showing1;
    private Showing showing2;
    private Movie movie1;
    private Movie movie2;

    boolean dataIsInitialized = false;

    @BeforeEach
    void setUp(){
        if (dataIsInitialized) return;
        movie1 = new Movie("Mamma Mia", "12", "125 min", "Musical");
        movieRepository.save(movie1);
        movie2 = new Movie("Inception", "16", "135 min", "Thriller");
        movieRepository.save(movie2);

        showing1 = Showing.builder()
                .time(LocalTime.of(16, 30))
                .date(LocalDate.now().plusDays(2))
                .type(ShowingType.MORNING)
                .movie(movie1)
                .theater(theaterRepository.save(new Theater(1, 20, 10))).build();
        showing2 = Showing.builder()
                .time(LocalTime.of(14, 30))
                .type(ShowingType.MORNING)
                .date(LocalDate.now().plusDays(1))
                .movie(movie2)
                .theater(theaterRepository.save(new Theater(2, 25, 16))).build();
        showingRepository.saveAndFlush(showing1);
        showingRepository.saveAndFlush(showing2);
        service = new ShowingService(showingRepository, movieRepository, theaterRepository);

        dataIsInitialized = true;
    }
    @Test
    void createShowingInPastThrow() {
        ShowingRequest newShowing = ShowingRequest.builder()
                .date(LocalDate.now().minusDays(1))
                .time(LocalTime.of(16, 30))
                .timeAfterShowing(30)
                .premiere(1)
                .movieId(movie1.getId())
                .theaterId(1).build();


        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> service.createShowing(newShowing));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Date is in the past", exception.getReason());
    }

    @Test
    void createShowingOverlappingThrow() {
        ShowingRequest newShowing = ShowingRequest.builder()
                .date(LocalDate.now().plusDays(1))
                .time(LocalTime.of(14, 45))
                .timeAfterShowing(30)
                .premiere(0)
                .movieId(movie2.getId())
                .theaterId(1).build();

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> service.createShowing(newShowing));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Time is overlapping another showing", exception.getReason());
    }

    @Test
    void createShowingValidTime() {
        ShowingRequest newShowing = ShowingRequest.builder()
                .date(LocalDate.now().plusDays(1))
                .time(LocalTime.of(17, 30))
                .timeAfterShowing(30)
                .premiere(0)
                .movieId(movie2.getId())
                .theaterId(1).build();

        ShowingResponse res = service.createShowing(newShowing);
        assertEquals(1, res.getMovieId());
    }

    @Test
    void getShowings() {
        List<ShowingResponse> responses = service.getShowings();
        assertEquals(2, responses.size());
    }

    @Test
    void getShowingsByDate() {
        List<ShowingResponse> responses = service.getShowingsByDate(LocalDate.now().plusDays(1));
        assertEquals(1, responses.size());
        assertEquals(2, responses.get(0).getMovieId());
    }
}