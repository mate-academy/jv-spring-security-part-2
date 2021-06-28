package mate.academy.spring.service.impl;

import static org.springframework.security.core.userdetails.User.withUsername;

import java.util.Arrays;
import mate.academy.spring.model.Role;
import mate.academy.spring.model.User;
import mate.academy.spring.service.UserService;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userService.findByEmail(email);
        UserBuilder userBuilder;
        userBuilder = withUsername(email);
        userBuilder.password(user.getPassword());
        userBuilder.roles(Arrays.toString(user.getRoles().stream()
                .toArray(Role[]::new)));
        return userBuilder.build();
    }
}
