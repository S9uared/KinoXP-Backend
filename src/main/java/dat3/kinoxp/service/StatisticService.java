package dat3.kinoxp.service;

import dat3.kinoxp.dto.StatisticRequest;
import dat3.kinoxp.dto.StatisticResponse;
import dat3.kinoxp.entity.Statistic;
import dat3.kinoxp.repository.StatisticRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticService {

    StatisticRepository statisticRepository;

    public StatisticService(StatisticRepository statisticRepository) {
        this.statisticRepository = statisticRepository;
    }

    public List<StatisticResponse> getStatistics(){
        List<Statistic> statistics = statisticRepository.findAll();
        //Maps statistics list to a statisticReponse list.
        return statistics.stream().map((statistic) -> new StatisticResponse(statistic)).toList();
    }

    public List<StatisticResponse> getStatisticsById(int id){
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
        //Movie movie = movieRepository.findById(body.getMovieId()).orElseThrow(
        //            () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No member with this id found"));;

        int totalReservations = calculateStatisticForMovie(body.getMovieId(), body.getDate());
        Statistic newStat = statisticRepository.save(new Statistic(/*movie,*/body.getDate(), totalReservations));
        return new StatisticResponse(newStat);
    }

    private int calculateStatisticForMovie(int movieId, LocalDate date){
        double result = 100;
        int reservations = 0;
        //List contains last 7 days from given date.
        LocalDate currentDate;
        List<Integer> showingList = new ArrayList<>();
        for(int i = 0; i < 7; i++){
            currentDate = date.minusDays(i);
            //showingList = getShowingsByMovieIdAndDate(movieId, currentDate);
            for(Integer num : showingList){
                //reservations = getReservationCountByShowingId(num); Do this, or get a list and get .size() on the list instead.

                result += reservations;
            }

        }
        //Get list of all showings matching movieId and all dates in the list.
        //For each showing use method like get reservationsByShowingId from reservationRepo in a loop. And get size
        // of the returned list, or use a method that counts reservations instead if possible.
        //In every iteration of loop take theaterId from showing and get Theater size too. Calculate % by
        // dividing reservations with theatersize. Add them all together and divide by 7 at the end.

        result = result/7;
        return (int) result * 100;
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
