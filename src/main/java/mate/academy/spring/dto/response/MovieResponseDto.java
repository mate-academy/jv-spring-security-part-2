package mate.academy.spring.dto.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MovieResponseDto {
    private Long id;
    private String title;
    private String description;
}
