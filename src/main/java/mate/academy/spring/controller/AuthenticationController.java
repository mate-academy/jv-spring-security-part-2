package mate.academy.spring.controller;

import java.util.Set;
import javax.validation.Valid;
import mate.academy.spring.dto.request.UserRequestDto;
import mate.academy.spring.dto.response.UserResponseDto;
import mate.academy.spring.model.Role;
import mate.academy.spring.model.User;
import mate.academy.spring.service.AuthenticationService;
import mate.academy.spring.service.RoleService;
import mate.academy.spring.service.mapper.ResponseDtoMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private final AuthenticationService authService;
    private final ResponseDtoMapper<UserResponseDto, User> userDtoResponseMapper;
    private final RoleService roleService;

    public AuthenticationController(AuthenticationService authService,
                                    ResponseDtoMapper<UserResponseDto, User> userDtoResponseMapper,
                                    RoleService roleService) {
        this.authService = authService;
        this.userDtoResponseMapper = userDtoResponseMapper;
        this.roleService = roleService;
    }

    @PostMapping("/register")
    public UserResponseDto register(@RequestBody @Valid UserRequestDto requestDto) {
        Role defaultRole = roleService.getByName(Role.RoleName.USER);
        User user = authService.register(requestDto.getEmail(), requestDto.getPassword());
        user.setRoles(Set.of(defaultRole));
        return userDtoResponseMapper.mapToDto(user);
    }
}
