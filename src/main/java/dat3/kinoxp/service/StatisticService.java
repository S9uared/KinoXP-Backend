package dat3.kinoxp.service;

import dat3.kinoxp.dto.StatisticRequest;
import dat3.kinoxp.dto.StatisticResponse;
import dat3.kinoxp.entity.Statistic;
import dat3.kinoxp.repository.StatisticRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public StatisticResponse getOneStatisticById(int id){
        Statistic stat = findStatById(id);
        return new StatisticResponse(stat);
    }

    public StatisticResponse addStatistic(StatisticRequest body){
        if(statisticRepository.existsById(body.getId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This statistic already exists");
        }
        //Movie movie = movieRepository.findById(body.getMovieId()).orElseThrow(
        //            () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No member with this id found"));;

        Statistic newStat = statisticRepository.save(new Statistic(/*movie,*/body.getDate(), body.getTotalReservations()));
        return new StatisticResponse(newStat);
    }

    public void deleteStatById(int id){
        Statistic stat = findStatById(id);
        statisticRepository.delete(stat);
    }

    private Statistic findStatById(int id){
        return statisticRepository.findById(id).
                orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Statistic with that id, does not exist"));
    }
}
