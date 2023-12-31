package dat3.kinoxp.service;

import dat3.kinoxp.dto.ShowingRequest;
import dat3.kinoxp.dto.ShowingResponse;
import dat3.kinoxp.entity.Movie;
import dat3.kinoxp.entity.Showing;
import dat3.kinoxp.entity.ShowingType;
import dat3.kinoxp.entity.Theater;
import dat3.kinoxp.repository.MovieRepository;
import dat3.kinoxp.repository.ReservationRepository;
import dat3.kinoxp.repository.ShowingRepository;
import dat3.kinoxp.repository.TheaterRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ShowingService {
    ShowingRepository showingRepository;
    MovieRepository movieRepository;
    TheaterRepository theaterRepository;
    ReservationRepository reservationRepository;

    public ShowingService(ShowingRepository showingRepository, MovieRepository movieRepository, TheaterRepository theaterRepository, ReservationRepository reservationRepository) {
        this.showingRepository = showingRepository;
        this.movieRepository = movieRepository;
        this.theaterRepository = theaterRepository;
        this.reservationRepository = reservationRepository;
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

        List<Showing> showingsOnDate = showingRepository.getShowingsByDateAndTheaterId(body.getDate(), body.getTheaterId());

        // Check for overlap with existing showings
        for (Showing s : showingsOnDate) {
            if (!(showingEndTime.isBefore(s.getTime()) || showingStartTime.isAfter(s.getEndingTime()))) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Time is overlapping another showing");
            }
        }

        // If no overlap is found, create the showing
        Showing showing = showingRepository.save(new Showing(body.getDate(), body.getTime(), getShowingType(body.isPremiere(), body.getTime()), showingEndTime, movie, theater));
        return new ShowingResponse(showing, true);
    }


    public List<ShowingResponse> getShowings(){
        List<Showing> showings = showingRepository.findFutureShowingsOrderByDateAndTime(LocalDate.now());
        return showings.stream().map(showing -> new ShowingResponse(showing, true)).toList();
    }


    public List<ShowingResponse> getShowingsByDate(LocalDate date){
        List<Showing> showings = showingRepository.getShowingsByDate(date);
        return showings.stream().map(showing -> new ShowingResponse(showing, false)).toList();
    }

    public List<ShowingResponse> getShowingsByMovieAndDate(int movieId, LocalDate date){
        List<Showing> showings = showingRepository.getShowingsByMovieIdAndDate(movieId, date);
        return showings.stream().map(showing -> new ShowingResponse(showing, false)).toList();
    }

    public ShowingResponse findById(int showingId){
        Showing showing = getShowingById(showingId);
        return new ShowingResponse(showing, false);
    }

    public ShowingResponse editShowing(ShowingRequest body, int showingId){
        Showing showing = getShowingById(showingId);
        Movie movie = movieRepository.findById(body.getMovieId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie with this id does not exist"));
        Theater theater = theaterRepository.findById(body.getTheaterId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Theater with this id does not exist"));

        // Not working - is compared to the existing showing in db and therefore cannot edit
        checkForOverlapAndThrowException(body, movie, showingId);

        showing.setDate(body.getDate());
        showing.setTime(body.getTime());
        showing.setMovie(movie);
        showing.setTheater(theater);
        showing.setType(getShowingType(body.isPremiere(), body.getTime()));
        Showing saved = showingRepository.save(showing);
        return new ShowingResponse(saved, true);
    }

    public void deleteShowingById(int showingId){
        if(reservationRepository.findAllByShowingId(showingId).size() > 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There are reservations on this showing");
        }
        Showing showing = getShowingById(showingId);
        showingRepository.delete(showing);
    }

    private ShowingType getShowingType(boolean isPremiere, LocalTime startTime) {
        ShowingType type;
        if(isPremiere){
            type = ShowingType.PREMIERE;
        } else if (startTime.isAfter(LocalTime.of(8, 0)) && startTime.isBefore(LocalTime.of(18, 0))) {
            type = ShowingType.REGULAR;
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

    private void checkForOverlapAndThrowException(ShowingRequest body, Movie movie, int showingId) {
        LocalTime showingStartTime = body.getTime();
        LocalTime showingEndTime = showingStartTime.plusMinutes(parseRuntime(movie.getRuntime())).plusMinutes(body.getCleaningTime());

        List<Showing> showingsOnDate = showingRepository.getShowingsByDateAndTheaterId(body.getDate(), body.getTheaterId());

        // Check for overlap with existing showings
        for (Showing s : showingsOnDate) {
            /*if (!(showingEndTime.isBefore(s.getTime()) || showingStartTime.isAfter(s.getEndingTime()))) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Time is overlapping another showing");
            }*/
            if (s.getId() != showingId && // Exclude the edited showing
                    !(showingEndTime.isBefore(s.getTime()) || showingStartTime.isAfter(s.getEndingTime()))) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Time is overlapping another showing");
            }
        }
    }

}
