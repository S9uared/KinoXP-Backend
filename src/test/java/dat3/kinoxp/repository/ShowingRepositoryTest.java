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

    boolean dataInitialized = false;
    int showingOneId, showingTwoId;

    @BeforeEach
    void setUp(){
        if(!dataInitialized){
            Movie movie1 = new Movie();
            Theater theater1 = new Theater();
            Showing showing1 = showingRepository.save(new Showing(LocalDate.of(2023, 10, 05), LocalTime.now(), movie1, theater1));
            showingOneId = showing1.getId();

            Movie movie2 = new Movie();
            Theater theater2 = new Theater();
            Showing showing2 = showingRepository.save(new Showing(LocalDate.of(2023, 12, 01), LocalTime.now(), movie2, theater2));
            showingTwoId = showing2.getId();
        }
    }

    @Test
    public void testFindById(){
        Showing showing = showingRepository.findById(showingOneId).get();
        assertEquals(LocalDate.now(), showing.getDate());
        assertEquals("Mamma Mia", showing.getMovie().getName());
    }

    @Test
    public void testCount(){
        assertEquals(2, showingRepository.count());
    }

    @Test
    public void testGetByDate(){
        assertEquals(1, showingRepository.getShowingsByDate(LocalDate.of(2023, 10, 05)).size());
    }
}