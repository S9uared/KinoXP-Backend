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

    // Security -> Anonymous
    @GetMapping
    List<ShowingResponse> getShowings(){
        return showingService.getShowings();
    }

    // Security -> Anonymous
    @GetMapping(path = "/date/{date}")
    List<ShowingResponse> getShowingsByDate(@PathVariable("date") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date) throws Exception{
        return showingService.getShowingsByDate(date);
    }

    @GetMapping(path = "/{movieId}/{date}")
    List<ShowingResponse> getShowingsByMovieAndDate(@PathVariable int movieId, @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date) throws Exception{
        return showingService.getShowingsByMovieAndDate(movieId, date);
    }

    // Security -> Anonymous
    @GetMapping(path = "/{id}")
    ShowingResponse getShowingById(@PathVariable int id){
        return showingService.findById(id);
    }

    // Security -> ADMIN
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ShowingResponse addShowing(@RequestBody ShowingRequest body){
        return showingService.createShowing(body);
    }

    // Security -> ADMIN
    @PutMapping("/{id}")
    ShowingResponse editShowing(@PathVariable int id, @RequestBody ShowingRequest body){
        return showingService.editShowing(body, id);
    }

    // Security -> ADMIN
    @DeleteMapping("/{id}")
    void deleteShowingById(@PathVariable int id){
        showingService.deleteShowingById(id);
    }
}
