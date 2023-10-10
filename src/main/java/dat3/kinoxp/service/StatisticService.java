package dat3.kinoxp.service;

import dat3.kinoxp.dto.StatisticRequest;
import dat3.kinoxp.dto.StatisticResponse;
import dat3.kinoxp.entity.*;
import dat3.kinoxp.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class StatisticService {

    StatisticRepository statisticRepository;
    MovieRepository movieRepository;
    TheaterRepository theaterRepository;
    ShowingRepository showingRepository;
    ReservationRepository reservationRepository;

    public StatisticService(StatisticRepository statisticRepository, MovieRepository movieRepository, TheaterRepository theaterRepository, ShowingRepository showingRepository, ReservationRepository reservationRepository) {
        this.statisticRepository = statisticRepository;
        this.movieRepository = movieRepository;
        this.theaterRepository = theaterRepository;
        this.showingRepository = showingRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<StatisticResponse> getStatistics(){
        List<Statistic> statistics = statisticRepository.findAll();
        return statistics.stream().map((statistic) -> new StatisticResponse(statistic)).toList();
    }

    public List<StatisticResponse> getStatisticsByMovieId(int id){
        List<Statistic> statistics = statisticRepository.getStatisticsByMovieId(id);
        return statistics.stream().map((statistic) -> new StatisticResponse(statistic)).toList();
    }

    //Can be used if edit feature on statistics is added.
    public StatisticResponse getOneStatisticById(int id){
        Statistic stat = findStatById(id);
        return new StatisticResponse(stat);
    }

    public StatisticResponse addStatistic(StatisticRequest body){
        if(body.getDate().isAfter(LocalDate.now())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date in the future is not allowed. Choose a current or previous date for this statistic");
        }
        Movie movie = movieRepository.findById(body.getMovieId()).orElseThrow(
                   () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No member with this id found"));

        int totalReservations = calculateStatisticForMovie(body.getMovieId(), body.getDate());
        Statistic newStat = statisticRepository.save(new Statistic(movie, body.getDate(), totalReservations));
        return new StatisticResponse(newStat);
    }

    private int calculateStatisticForMovie(int movieId, LocalDate date){
        double result = 0;
        List<Reservation> reservations;
        Theater theater;
        int theaterSeats;

        //Only counts when a showing is found on a specific date. So if a movie only has showing on 3 showings in last week.
        // The average is calculated based on that. The total reservations all added, is divided by this number.
        int showingCounter = 0;

        Movie movie = movieRepository.findById(movieId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No member with this id found"));
        List<Showing> showingList = showingRepository.getShowingsByMovieId(movie.getId());
        LocalDate currentDate;
        for(int i = 0; i < 7; i++){
            currentDate = date.minusDays(i);
            for(int j = 0; j < showingList.size(); j++){
                if(showingList.get(j).getDate().isEqual(currentDate)){
                    showingCounter++;
                    theater = theaterRepository.findById(showingList.get(j).getTheater().getId()).orElseThrow(
                            () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No Theater with this id found"));
                    theaterSeats = theater.getRows()*theater.getSeatsPerRow();
                    reservations = reservationRepository.findAllByShowingId(showingList.get(j).getId());
                    for(Reservation res : reservations){
                        result += (double) res.getSeats().size()/theaterSeats;
                    }
                }
            }
        }
        //Get list of all showings matching movieId and all dates in the list.
        //For each showing use method like get reservationsByShowingId from reservationRepo in a loop. And get size
        // of the returned list, or use a method that counts reservations instead if possible.
        //In every iteration of loop take theaterId from showing and get Theater size too. Calculate % by
        // dividing reservations with theatersize. Add them all together and divide by 7 at the end.

        result = result/ showingCounter;
        result = result * 100;
        return (int) result;
    }


    public ResponseEntity<Boolean> deleteStatById(int id){
        if(!statisticRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Statistic with this id does not exist");
        }
        try{
            statisticRepository.deleteById(id);
            return ResponseEntity.ok(true);
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not delete statistic. Maybe because it is connected to a current movie.");
        }
    }

    private Statistic findStatById(int id){
        return statisticRepository.findById(id).
                orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Statistic with that id, does not exist"));
    }



}
