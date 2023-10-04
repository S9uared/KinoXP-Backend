package dat3.kinoxp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import dat3.kinoxp.entity.Showing;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShowingResponse {
    private int id;
    @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate date;
    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime time;
    private Movie movie;
    private Theater theater;

    public ShowingResponse(Showing showing){
        this.id = showing.getId();
        this.date = showing.getDate();
        this.time = showing.getTime();
        this.movie = showing.getMovie();
        this.theater = showing.getTheater();
    }
}
