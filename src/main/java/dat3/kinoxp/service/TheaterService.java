package dat3.kinoxp.service;

import dat3.kinoxp.dto.TheaterRequest;
import dat3.kinoxp.dto.TheaterResponse;
import dat3.kinoxp.entity.Theater;
import dat3.kinoxp.repository.TheaterRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TheaterService {
    TheaterRepository theaterRepository;

    public TheaterService(TheaterRepository theaterRepository) {
        this.theaterRepository = theaterRepository;
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
        return new TheaterResponse(newTheater);
    }
    //EditTheater(body, id)
    public TheaterResponse editTheater(TheaterRequest body){
        Theater editTheater = getTheaterById(body.getId());
        editTheater.setRows(body.getRows());
        editTheater.setSeatsPerRow(body.getSeatsPerRow());
        editTheater = theaterRepository.save(editTheater);
        return new TheaterResponse(editTheater);
    }

    //DeleteTheater
    public void deleteTheaterById(int theaterId){
        Theater theater = getTheaterById(theaterId);
        theaterRepository.delete(theater);
    }

    private Theater getTheaterById(int theaterId){
        return theaterRepository.findById(theaterId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No Theater with this id found"));
    }


}
