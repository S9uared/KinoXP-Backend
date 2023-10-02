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

    String movieName;

    @JsonFormat(pattern = "yyyy-MM-dd",shape = JsonFormat.Shape.STRING)
    LocalDate date;

    int totalReservations;

    public StatisticResponse(Statistic s){
        //Implement after merge with movie branch.
        //movieName = s.getMovie().getName();
        date = s.getDate();
        totalReservations = s.getTotalReservations();
    }
}
