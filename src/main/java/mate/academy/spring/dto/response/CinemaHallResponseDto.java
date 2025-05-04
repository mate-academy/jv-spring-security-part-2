package mate.academy.spring.dto.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CinemaHallResponseDto {
    private Long id;
    private int capacity;
    private String description;
}
