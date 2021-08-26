package mate.academy.spring.security;

import static org.springframework.security.core.userdetails.User.UserBuilder;
import static org.springframework.security.core.userdetails.User.withUsername;

import mate.academy.spring.exception.DataProcessingException;
import mate.academy.spring.model.User;
import mate.academy.spring.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = null;
        try {
            user = userService.findByEmail(username);
        } catch (DataProcessingException e) {
            throw new UsernameNotFoundException("Can't find user by email=" + username, e);
        }
        UserBuilder builder = withUsername(username);
        builder.password(user.getPassword());
        builder.roles(user.getRoles()
                .stream()
                .map(role -> role.getName().name())
                .toArray(String[]::new));
        return builder.build();
    }
}
