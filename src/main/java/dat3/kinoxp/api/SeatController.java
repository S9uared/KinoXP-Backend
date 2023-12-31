package dat3.kinoxp.api;

import dat3.kinoxp.dto.SeatRequest;
import dat3.kinoxp.dto.SeatResponse;
import dat3.kinoxp.service.SeatService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    // Security -> Anonymous
    @GetMapping("/theater/{id}")
    List<SeatResponse> getSeatsByTheaterId(@PathVariable int id){
        return seatService.getSeatsByTheaterId(id);
    }

    // Security -> ADMIN
    @GetMapping("/{id}")
    SeatResponse getOneSeatById(@PathVariable int id){
        return seatService.getOneSeat(id);
    }

    // Security -> ADMIN
    @GetMapping("/{id}/{type}")
    List<SeatResponse> getSeatsByTheaterIdAndType(@PathVariable int id, @PathVariable String type){
        return seatService.getSeatsByTheaterIdAndType(id, type);
    }

    // Security -> ADMIN
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    SeatResponse addSeat(@RequestBody SeatRequest body){
        return seatService.addSeat(body);
    }

    // Security -> ADMIN
    @PutMapping("/{id}")
    SeatResponse editSeat(@RequestBody SeatRequest body, @PathVariable int id){
        return seatService.editSeat(body, id);
    }
    // Security -> ADMIN
    @DeleteMapping("/{id}")
    void deleteSeat(@PathVariable int id) {
        seatService.deleteSeatById(id);
    }
}
