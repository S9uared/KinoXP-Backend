package dat3.kinoxp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dat3.kinoxp.entity.Showing;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowingRequest {
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate date;
    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime time;
    private int movieId;
    private int theaterId;
}
