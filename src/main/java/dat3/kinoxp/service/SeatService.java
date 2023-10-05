package dat3.kinoxp.service;

import dat3.kinoxp.dto.SeatRequest;
import dat3.kinoxp.dto.SeatResponse;
import dat3.kinoxp.entity.Seat;
import dat3.kinoxp.entity.Theater;
import dat3.kinoxp.repository.SeatRepository;
import dat3.kinoxp.repository.TheaterRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SeatService {
    //TODO
    SeatRepository seatRepository;
    TheaterRepository theaterRepository;

    public SeatService(SeatRepository seatRepository, TheaterRepository theaterRepository) {
        this.seatRepository = seatRepository;
        this.theaterRepository = theaterRepository;
    }


    public List<SeatResponse> getSeatsByTheaterId(int id){
        List<Seat> seats = seatRepository.getSeatsByTheaterId(id);
        return seats.stream().map(seat -> new SeatResponse(seat)).toList();
    }

    public SeatResponse addSeat(SeatRequest body){
        if(seatRepository.existsByTheaterIdAndRowNumberAndSeatNumber(body.getTheaterId(), body.getRowNumber(), body.getSeatNumber())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Seat already exists in theater");
        }
        Theater theater = theaterRepository.findById(body.getTheaterId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No Theater with this id found"));
        Seat newSeat = seatRepository.save(new Seat(theater, body.getRowNumber(), body.getSeatNumber(), body.getStatus(), body.getType()));
        return new SeatResponse(newSeat);
    }

    public SeatResponse editSeat(SeatRequest body, int id){
        Seat editSeat = getSeatById(id);
        //Nødvendigt? Virker bare ulogisk at ændre i sædets position nogensinde, hvis der rykkes rundt på en kinosal. Bare lav nye istedet.
        if(!seatRepository.existsByTheaterIdAndRowNumberAndSeatNumber(body.getTheaterId(), body.getRowNumber(), body.getSeatNumber())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Can not edit the theater, seat number and or row number of seat. Remove or add new seats instead");
        }
        editSeat.setType(body.getType());
        editSeat.setStatus(body.getStatus());
        seatRepository.save(editSeat);
        return new SeatResponse(editSeat);
    }

    public void deleteSeatById(int seatId){
        Seat seat = getSeatById(seatId);
        seatRepository.delete(seat);
    }

    private Seat getSeatById(int seatId){
        return seatRepository.findById(seatId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No Theater with this id found"));
    }

}
