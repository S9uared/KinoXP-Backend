package dat3.kinoxp.repository;

import dat3.kinoxp.entity.Showing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ShowingRepositoryTest {

    @Autowired
    ShowingRepository showingRepository;
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    TheaterRepository theaterRepository;


    boolean dataInitialized = false;
    int showingOneId, showingTwoId;

    @BeforeEach
    void setUp(){
        if(!dataInitialized){
            Movie movie1 = new Movie("Mamma Mia", 12, "Musical");
            movieRepository.save(movie1);
            Theater theater1 = new Theater(1, 400);
            theaterRepository.save(theater1);
            Showing showing1 = showingRepository.save(new Showing(LocalDate.of(2023, 10, 03), LocalTime.of(16, 30), movie1, theater1));
            showingOneId = showing1.getId();

            Movie movie2 = new Movie("Inception", 16, "Thriller");
            movieRepository.save(movie2);
            Theater theater2 = new Theater(2, 200);
            theaterRepository.save(theater2);
            Showing showing2 = showingRepository.save(new Showing(LocalDate.of(2023, 12, 01), LocalTime.of(12, 45), movie2, theater2));
            showingTwoId = showing2.getId();
        }
    }

    @Test
    public void testFindById(){
        Showing showing = showingRepository.findById(showingOneId).get();
        assertEquals(LocalDate.now(), showing.getDate());
        assertEquals("Mamma Mia", showing.getMovie().getMovieName());
    }

    @Test
    public void testCount(){
        assertEquals(2, showingRepository.count());
    }

    @Test
    public void testGetByDate(){
        assertEquals(1, showingRepository.getShowingsByDate(LocalDate.of(2023, 10, 03)).size());
    }

    @Test
    public  void testGetByMovie(){
        assertEquals(1, showingRepository.getShowingsByMovieId(1).size());
        assertEquals("Mamma Mia", showingRepository.getShowingsByMovieId(1).get(0).getMovie().getMovieName());
    }

    @Test
    public  void testGetByMovieCategory(){
        assertEquals(1, showingRepository.getShowingsByMovieCategory("Thriller").size());
        assertEquals("Musical", showingRepository.getShowingsByMovieId(1).get(0).getMovie().getCategory());
    }
}