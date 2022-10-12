package mate.academy.spring.dto.request;

import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public class MovieRequestDto {
    @NotEmpty(message = "Please enter title of movie")
    private String title;
    @Length(max = 255, message = "Please note, that description must be less than 255 characters")
    private String description;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
