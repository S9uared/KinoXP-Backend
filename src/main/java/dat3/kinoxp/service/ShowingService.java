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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public ShowingResponse createShowing(ShowingRequest body) {
        Movie movie = movieRepository.findById(body.getMovieId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie with this id found"));
        Theater theater = theaterRepository.findById(body.getTheaterId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No theater with this id found"));

        if (body.getDate().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date is in the past");
        }

        LocalTime showingStartTime = body.getTime();
        LocalTime showingEndTime = showingStartTime.plusMinutes(parseRuntime(movie.getRuntime())).plusMinutes(body.getCleaningTime());

        List<Showing> showingsOnDate = showingRepository.getShowingsByDate(body.getDate());

        // Check for overlap with existing showings
        for (Showing s : showingsOnDate) {
            if (!(showingEndTime.isBefore(s.getTime()) || showingStartTime.isAfter(s.getEndingTime()))) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Time is overlapping another showing");
            }
        }

        // If no overlap is found, create the showing
        Showing showing = showingRepository.save(new Showing(body.getDate(), body.getTime(), getShowingType(body), showingEndTime, movie, theater));
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
        showing.setType(getShowingType(body));
        Showing saved = showingRepository.save(showing);
        return new ShowingResponse(saved);
    }

    public void deleteShowingById(int showingId){
        Showing showing = getShowingById(showingId);
        showingRepository.delete(showing);
    }

    private ShowingType getShowingType(ShowingRequest body) {
        ShowingType type;
        if (body.getTime().isAfter(LocalTime.of(8, 0)) && body.getTime().isBefore(LocalTime.of(18, 0))) {
            type = ShowingType.MORNING;
        } else if (body.getPremiere() == 1) {
            type = ShowingType.PREMIERE;
        } else {
            type = ShowingType.EVENING;
        }
        return type;
    }

    private Showing getShowingById(int showingId){
        return showingRepository.findById(showingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Showing with this id does not exist"));
    }

    private int parseRuntime(String input) {
        int minutes = 0;

        // Define a regular expression pattern to match hours and minutes
        Pattern pattern = Pattern.compile("(\\d+) min");
        Matcher matcher = pattern.matcher(input);

        // Check if the pattern matches
        if (matcher.find()) {
            // Extract hours and minutes as strings
            String minutesStr = matcher.group(1);

            // Convert the extracted strings to integers
            minutes = Integer.parseInt(minutesStr);
        }

        return minutes;
    }

}
