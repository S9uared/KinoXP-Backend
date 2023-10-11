package dat3.kinoxp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import dat3.kinoxp.entity.Statistic;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatisticResponse {

    int id;
    String movieName;
    int movieId;

    @JsonFormat(pattern = "dd-MM-yyyy",shape = JsonFormat.Shape.STRING)
    LocalDate date;

    int totalReservations;

    public StatisticResponse(Statistic s){
        this.id = s.getId();
        this.movieName = s.getMovie().getTitle();
        this.movieId = s.getMovie().getId();
        this.date = s.getDate();
        this.totalReservations = s.getTotalReservations();
    }
}
