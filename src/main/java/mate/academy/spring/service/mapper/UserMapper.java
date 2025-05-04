package mate.academy.spring.service.mapper;

import java.util.List;
import mate.academy.spring.dto.response.UserResponseDto;
import mate.academy.spring.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements ResponseDtoMapper<UserResponseDto, User> {
    @Override
    public UserResponseDto mapToDto(User user) {
        List<String> roles = user.getRoles().stream()
                .map(role -> role.getRoleName().name())
                .toList();
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(user.getId());
        responseDto.setEmail(user.getEmail());
        responseDto.setRoles(roles);
        return responseDto;
    }
}
