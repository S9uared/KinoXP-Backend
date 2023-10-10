package dat3.kinoxp.api;

import dat3.kinoxp.dto.TheaterRequest;
import dat3.kinoxp.dto.TheaterResponse;
import dat3.kinoxp.entity.Theater;
import dat3.kinoxp.service.TheaterService;
import org.springframework.http.MediaType;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theaters")
public class TheaterController {

    TheaterService theaterService;

    public TheaterController(TheaterService theaterService) {
        this.theaterService = theaterService;
    }

    //Security -> ADMIN
    @GetMapping
    List<TheaterResponse> getTheaters(){
        return theaterService.getTheaters();
    }

    //Security -> Anonymous
    @GetMapping("/{id}")
    TheaterResponse getTheaterById(@PathVariable int id){
        return theaterService.getOneTheater(id);
    }

    //Security -> ADMIN
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    TheaterResponse addTheater(@RequestBody TheaterRequest body){
        return theaterService.createTheater(body);
    }

    //Security -> ADMIN
    @PutMapping("/{id}")
    TheaterResponse editTheater(@RequestBody TheaterRequest body, @PathVariable int id){
        return theaterService.editTheater(body, id);
    }

    //Security -> ADMIN
    @DeleteMapping("/{id}")
    void deleteTheater(@PathVariable int id){
        theaterService.deleteTheaterById(id);
    }
}
