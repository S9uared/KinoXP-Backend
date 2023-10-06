package dat3.kinoxp.dto;

import dat3.kinoxp.entity.Theater;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TheaterRequest
{
    private int id;
    private int rows;
    private int seatsPerRow;

    public Theater getTheaterEntity(TheaterRequest request){
        return new Theater(
                request.getId(),
                request.getRows(),
                request.getSeatsPerRow()
        );
    }
}
