package mate.academy.spring.dto.response;

import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ShoppingCartResponseDto {
    private Long userId;
    private List<Long> ticketIds;
}
