package dat3.kinoxp.dto;

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
}
