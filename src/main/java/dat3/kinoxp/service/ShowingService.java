package dat3.kinoxp.service;

import dat3.kinoxp.dto.ShowingRequest;
import dat3.kinoxp.dto.ShowingResponse;
import dat3.kinoxp.entity.Movie;
import dat3.kinoxp.entity.Showing;
import dat3.kinoxp.entity.Theater;
import dat3.kinoxp.repository.MovieRepository;
import dat3.kinoxp.repository.ShowingRepository;
import dat3.kinoxp.repository.TheaterRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class ShowingService {
    ShowingRepository showingRepository;
    MovieRepository movieRepository;
    TheaterRepository theaterRepository;

    public ShowingService(ShowingRepository showingRepository, MovieRepository movieRepository, TheaterRepository theaterRepository) {
        this.showingRepository = showingRepository;
        this.movieRepository = movieRepository;
        this.theaterRepository = theaterRepository;
    }

    public ShowingResponse createShowing(ShowingRequest body){
        if (body.getDate().isBefore(LocalDate.now())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date is in the past");
        }
        List<Showing> showingsOnDate = showingRepository.getShowingsByDate(body.getDate());
        for (Showing s: showingsOnDate){
            if (!body.getTime().plusHours(2).minusMinutes(1).isAfter(s.getTime()) || !body.getTime().isAfter(s.getTime().plusHours(2).minusMinutes(1))){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Time is overlapping another showing");
            }
        }
        Movie movie = movieRepository.findById(body.getMovieId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No movie with this id found"));
        Theater theater = theaterRepository.findById(body.getTheaterId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No theater with this id found"));
        Showing showing = showingRepository.save(new Showing(body.getDate(), body.getTime(), movie, theater));
        return new ShowingResponse(showing);
    }

    public List<ShowingResponse> getShowings(){
        List<Showing> showings = showingRepository.findAll();
        return showings.stream().map(showing -> new ShowingResponse(showing)).toList();
    }

    public List<ShowingResponse> getShowingsByDate(LocalDate date){
        List<Showing> showings = showingRepository.getShowingsByDate(date);
        return showings.stream().map(showing -> new ShowingResponse(showing)).toList();
    }

    public ShowingResponse findById(int showingId){
        Showing showing = getShowingById(showingId);
        return new ShowingResponse(showing);
    }

    public ShowingResponse editShowing(ShowingRequest body, int showingId){
        Showing showing = getShowingById(showingId);
        Movie movie = movieRepository.findById(body.getMovieId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie with this id does not exist"));
        Theater theater = theaterRepository.findById(body.getTheaterId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Theater with this id does not exist"));
        showing.setDate(body.getDate());
        showing.setTime(body.getTime());
        showing.setMovie(movie);
        showing.setTheater(theater);
        Showing saved = showingRepository.save(showing);
        return new ShowingResponse(saved);
    }

    private Showing getShowingById(int showingId){
        return showingRepository.findById(showingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Showing with this id does not exist"));
    }

    public void deleteShowingById(int showingId){
        Showing showing = getShowingById(showingId);
        showingRepository.delete(showing);
    }
}
