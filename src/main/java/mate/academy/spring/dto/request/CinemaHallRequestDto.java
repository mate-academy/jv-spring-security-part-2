package mate.academy.spring.dto.request;

import javax.validation.constraints.DecimalMin;
import org.hibernate.validator.constraints.Length;

public class CinemaHallRequestDto {
    @DecimalMin(value = "0", message = "Please enter capacity more than 0")
    private int capacity;
    @Length(max = 255, message = "Please note, that description must be less than 255 characters")
    private String description;

    public int getCapacity() {
        return capacity;
    }

    public String getDescription() {
        return description;
    }
}
