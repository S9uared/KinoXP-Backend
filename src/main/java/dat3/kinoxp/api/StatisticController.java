package dat3.kinoxp.api;

import dat3.kinoxp.dto.StatisticRequest;
import dat3.kinoxp.dto.StatisticResponse;
import dat3.kinoxp.service.StatisticService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/statistics")
public class StatisticController {
    StatisticService statisticService;

    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    //Security -> Admin?
    @GetMapping
    List<StatisticResponse> getStatistics(){
        return statisticService.getStatistics();
    }

    //Security -> Admin
    @GetMapping("/{id}")
    List<StatisticResponse> getStatisticsById(@PathVariable int id)throws Exception{
        return statisticService.getStatisticsById(id);
    }

    //Security -> Admin
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    StatisticResponse addStatistic(@RequestBody StatisticRequest body){
        return statisticService.addStatistic(body);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Boolean> deleteStatistic(@PathVariable int id) {
        return statisticService.deleteStatById(id);
    }

}
