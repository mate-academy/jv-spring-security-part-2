package mate.academy.spring.service;

import java.util.Optional;
import mate.academy.spring.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            org.springframework.security.core.userdetails.User.UserBuilder builder =
                    org.springframework.security.core.userdetails.User.withUsername(email);
            builder.password(user.getPassword());
            builder.roles(user.getRoles().stream()
                    .map(r -> r.getRoleName().name())
                    .toArray(String[]::new));
            return builder.build();
        }
        throw new UsernameNotFoundException("Can't find user with email: " + email);

    }
}
