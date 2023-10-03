package dat3.kinoxp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dat3.kinoxp.entity.Showing;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class ShowingRequest {
    @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate date;
    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime time;
    private int movieId;
    private int theaterId;

    public ShowingRequest(LocalDate date, LocalTime time, int movieId, int theaterId) {
        this.date = date;
        this.time = time;
        this.movieId = movieId;
        this.theaterId = theaterId;
    }
}
