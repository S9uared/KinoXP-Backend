package dat3.kinoxp.service;

import dat3.kinoxp.dto.ShowingRequest;
import dat3.kinoxp.dto.ShowingResponse;
import dat3.kinoxp.entity.Showing;
import dat3.kinoxp.repository.ShowingRepository;
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
}
