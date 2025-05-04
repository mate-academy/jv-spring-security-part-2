package mate.academy.spring.service.security;

import static org.springframework.security.core.userdetails.User.withUsername;

import java.util.stream.Collectors;
import mate.academy.spring.model.User;
import mate.academy.spring.service.UserService;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userService.findByEmail(userName).orElseThrow(
                () -> new UsernameNotFoundException("User '" + userName + "' not found"));
        UserBuilder userBuilder = withUsername(userName);
        userBuilder.password(user.getPassword());
        userBuilder.roles(user.getRoles()
                .stream()
                .map(role -> role.getRoleName().name())
                .collect(Collectors.joining(",")));
        return userBuilder.build();
    }
}
