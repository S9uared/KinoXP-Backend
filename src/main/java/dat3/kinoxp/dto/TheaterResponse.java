package dat3.kinoxp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dat3.kinoxp.entity.Theater;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TheaterResponse {
    int id;
    int rows;
    int seatsPerRow;

    public TheaterResponse(Theater theater){
        this.id = theater.getId();
        this.rows = theater.getRows();
        this.seatsPerRow = theater.getSeatsPerRow();
    }
}
