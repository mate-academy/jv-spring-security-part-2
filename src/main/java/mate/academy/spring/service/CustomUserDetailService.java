package mate.academy.spring.service;

import static org.springframework.security.core.userdetails.User.withUsername;

import java.util.Optional;
import mate.academy.spring.model.User;
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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userService.findByEmail(email);
        org.springframework.security.core.userdetails.User.UserBuilder builder;
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            builder = withUsername(email);
            builder.password(user.getPassword());
            builder.roles(user.getRoles().stream()
                    .map(role -> role.getName().name())
                    .toArray(String[]::new));
            return builder.build();
        }
        throw new UsernameNotFoundException("User with email: " + email + " not found.");
    }
}
