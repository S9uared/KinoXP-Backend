package dat3.kinoxp.api;

import dat3.kinoxp.dto.ShowingRequest;
import dat3.kinoxp.dto.ShowingResponse;
import dat3.kinoxp.entity.Showing;
import dat3.kinoxp.service.ShowingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/showings")
public class ShowingController {
    ShowingService showingService;

    public ShowingController(ShowingService showingService) {
        this.showingService = showingService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ShowingResponse addShowing(@RequestBody ShowingRequest body){
        return showingService.createShowing(body);
    }

    @GetMapping
    List<ShowingResponse> getShowings(){
        return showingService.getShowings();
    }

    @GetMapping(path = "/date/{date}")
    List<ShowingResponse> getShowingsByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) throws Exception{
        return showingService.getShowingsByDate(date);
    }

    @GetMapping(path = "/{id}")
    ShowingResponse getShowingById(@PathVariable int id){
        return showingService.findById(id);
    }

    @PutMapping("/{id}")
    ShowingResponse editShowing(@PathVariable int id, @RequestBody ShowingRequest body){
        return showingService.editShowing(body, id);
    }

    @DeleteMapping("/{id}")
    void deleteShowingById(@PathVariable int id){
        showingService.deleteShowingById(id);
    }
}
