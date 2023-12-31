package dat3.kinoxp.api;

import dat3.kinoxp.dto.StatisticRequest;
import dat3.kinoxp.dto.StatisticResponse;
import dat3.kinoxp.service.StatisticService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
public class StatisticController {
    StatisticService statisticService;

    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    //Security -> ADMIN
    @GetMapping
    List<StatisticResponse> getStatistics(){
        return statisticService.getStatistics();
    }

    //Security -> ADMIN
    @GetMapping("/movie/{id}")
    List<StatisticResponse> getStatisticsByMovieId(@PathVariable int id)throws Exception{
        return statisticService.getStatisticsByMovieId(id);
    }

    //Security -> ADMIN
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    StatisticResponse addStatistic(@RequestBody StatisticRequest body){
        return statisticService.addStatistic(body);
    }

    //Security -> ADMIN
    @DeleteMapping("/{id}")
    ResponseEntity<Boolean> deleteStatistic(@PathVariable int id) {
        return statisticService.deleteStatById(id);
    }

}
