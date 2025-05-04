package mate.academy.spring.service.mapper;

import java.util.stream.Collectors;
import mate.academy.spring.dto.response.UserResponseDto;
import mate.academy.spring.model.Role;
import mate.academy.spring.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements ResponseDtoMapper<UserResponseDto, User> {
    @Override
    public UserResponseDto mapToDto(User user) {
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(user.getId());
        responseDto.setEmail(user.getEmail());
        responseDto.setRoleIds(user.getRoles()
                .stream()
                .map(Role::getId)
                .collect(Collectors.toList()));
        return responseDto;
    }
}
