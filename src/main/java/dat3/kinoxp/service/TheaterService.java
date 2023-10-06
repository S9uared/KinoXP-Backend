package dat3.kinoxp.service;

import dat3.kinoxp.dto.TheaterRequest;
import dat3.kinoxp.dto.TheaterResponse;
import dat3.kinoxp.entity.Seat;
import dat3.kinoxp.entity.Theater;
import dat3.kinoxp.repository.SeatRepository;
import dat3.kinoxp.repository.ShowingRepository;
import dat3.kinoxp.repository.TheaterRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class TheaterService {
    TheaterRepository theaterRepository;
    SeatRepository seatRepository;
    ShowingRepository showingRepository;

    public TheaterService(TheaterRepository theaterRepository, SeatRepository seatRepository, ShowingRepository showingRepository)
    {
        this.theaterRepository = theaterRepository;
        this.seatRepository = seatRepository;
        this.showingRepository = showingRepository;
    }

    public List<TheaterResponse> getTheaters(){
        List<Theater> theaters = theaterRepository.findAll();
        return theaters.stream().map(theater -> new TheaterResponse(theater)).toList();
    }

    public TheaterResponse getOneTheater(int id){
        Theater theater = getTheaterById(id);
        return new TheaterResponse(theater);
    }

    public TheaterResponse createTheater(TheaterRequest body){
        Theater newTheater = body.getTheaterEntity(body);
        newTheater = theaterRepository.save(newTheater);
        updateSeatTableForTheater(newTheater);
        return new TheaterResponse(newTheater);
    }

    //EditTheater(body, id)
    public TheaterResponse editTheater(TheaterRequest body, int id){
        Theater editTheater = getTheaterById(id);
        editTheater.setRows(body.getRows());
        editTheater.setSeatsPerRow(body.getSeatsPerRow());
        editTheater = theaterRepository.save(editTheater);
        updateSeatTableForTheater(editTheater);
        return new TheaterResponse(editTheater);
    }

    private void updateSeatTableForTheater(Theater theater){
        //Remove all seats for theater
        List<Seat> seats = seatRepository.getSeatsByTheaterId(theater.getId());
        seatRepository.deleteAll(seats);

        //Add new seats matching rows and seatsperrow matching given theater
        List<Seat> newSeats = new ArrayList<>();
        for(int rowNumber = 1; rowNumber <= theater.getRows(); rowNumber++){
            for(int seatNumber = 1; seatNumber <= theater.getSeatsPerRow(); seatNumber++){
                newSeats.add(new Seat(theater, rowNumber, seatNumber, "available", "standard"));
            }
        }
        seatRepository.saveAll(newSeats);
    }

    //DeleteTheater
    public void deleteTheaterById(int theaterId){
        if(showingRepository.existsByTheaterId(theaterId)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Can not delete theater with active showings. Change or delete showings before deleting this theater");
        }
        Theater theater = getTheaterById(theaterId);
        List<Seat> seats = seatRepository.getSeatsByTheaterId(theaterId);
        seatRepository.deleteAll(seats);
        theaterRepository.delete(theater);
    }

    private Theater getTheaterById(int theaterId){
        return theaterRepository.findById(theaterId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No Theater with this id found"));
    }


}
