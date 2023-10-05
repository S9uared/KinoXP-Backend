package dat3.kinoxp.service;

import dat3.kinoxp.dto.SeatRequest;
import dat3.kinoxp.dto.SeatResponse;
import dat3.kinoxp.entity.Seat;
import dat3.kinoxp.repository.SeatRepository;
import dat3.kinoxp.repository.TheaterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService
{
    SeatRepository seatRepository;
    TheaterRepository theaterRepository;

    public SeatService(SeatRepository seatRepository, TheaterRepository theaterRepository) {
        this.seatRepository = seatRepository;
        this.theaterRepository = theaterRepository;
    }

    //TODO
    public List<SeatResponse> getSeatsByTheaterId(int id){
        List<Seat> seats = seatRepository.getSeatsByTheaterId(id);
        return seats.stream().map(seat -> new SeatResponse(seat)).toList();
    }

    public void addSeat(SeatRequest body){

    }

}
