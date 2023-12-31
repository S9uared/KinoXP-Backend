package dat3.kinoxp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dat3.kinoxp.entity.Statistic;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatisticRequest {

    private int movieId;

    @JsonFormat(pattern = "dd-MM-yyyy",shape = JsonFormat.Shape.STRING)
    private LocalDate date;
}
