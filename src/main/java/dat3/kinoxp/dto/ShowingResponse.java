package dat3.kinoxp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import dat3.kinoxp.entity.Movie;
import dat3.kinoxp.entity.Showing;
import dat3.kinoxp.entity.ShowingType;
import dat3.kinoxp.entity.Theater;
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
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate date;
    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime time;
    private ShowingType type;
    private int movieId;
    private int theaterId;

    public ShowingResponse(Showing showing){
        this.id = showing.getId();
        this.date = showing.getDate();
        this.time = showing.getTime();
        this.type = showing.getType();
        this.movieId = showing.getMovie().getId();
        this.theaterId = showing.getTheater().getId();
    }
}
