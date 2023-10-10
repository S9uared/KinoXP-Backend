package dat3.kinoxp.repository;

import dat3.kinoxp.entity.Movie;
import dat3.kinoxp.entity.Showing;
import dat3.kinoxp.entity.ShowingType;
import dat3.kinoxp.entity.Theater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DataJpaTest
class ShowingRepositoryTest {

/*    @Autowired
    ShowingRepository showingRepository;
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    TheaterRepository theaterRepository;


    boolean dataInitialized = false;
    int showingOneId, showingTwoId, movieOneId, movieTwoId;

    @BeforeEach
    void setUp(){
        if(!dataInitialized){
            Movie movie1 = new Movie("Mamma Mia", "12", "125 min", "Musical");

            movieRepository.save(movie1);
            movieOneId = movie1.getId();
            Theater theater1 = new Theater(1, 25, 16);
            theaterRepository.save(theater1);
            Showing showing1 = showingRepository.save(new Showing(LocalDate.now(), LocalTime.of(16, 30), ShowingType.EVENING, LocalTime.of(14, 15), movie1, theater1));
            showingOneId = showing1.getId();

            Movie movie2 = new Movie("Inception", "16", "135 min", "Thriller");

            movieRepository.save(movie2);
            movieTwoId = movie2.getId();
            Theater theater2 = new Theater(2, 20, 10);
            theaterRepository.save(theater2);
            Showing showing2 = showingRepository.save(new Showing(LocalDate.now(), LocalTime.of(12, 45), ShowingType.REGULAR, LocalTime.of(14, 15), movie2, theater2));
            showingTwoId = showing2.getId();
        }
    }

    @Test
    public void testFindById(){
        Showing showing = showingRepository.findById(showingOneId).get();
        assertEquals(LocalDate.now(), showing.getDate());
        assertEquals("Mamma Mia", showing.getMovie().getTitle());
    }

    @Test
    public void testCount(){
        assertEquals(2, showingRepository.count());
    }

    @Test
    public void testGetByDate(){
        assertEquals(2, showingRepository.getShowingsByDate(LocalDate.now()).size());
    }

    @Test
    public  void testGetByMovie(){
        assertEquals(1, showingRepository.getShowingsByMovieId(movieOneId).size());
        assertEquals(1, showingRepository.getShowingsByMovieId(1).size());
    }

    @Test
    public  void testGetByMovieGenre(){
        assertEquals(1, showingRepository.getShowingsByMovieGenre("Thriller").size());
        assertEquals("Musical", showingRepository.getShowingsByMovieId(movieOneId).get(0).getMovie().getGenre());
    }*/
}