package mate.academy.spring.service;

import java.util.Optional;
import mate.academy.spring.model.User;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userFromDB = userService.findByEmail(username);
        if (userFromDB.isEmpty()) {
            throw new UsernameNotFoundException("Can't find user by username: " + username);
        }
        org.springframework.security.core.userdetails.User.UserBuilder builder =
                org.springframework.security.core.userdetails.User.withUsername(username);
        builder.password(userFromDB.get().getPassword());
        builder.authorities(userFromDB.get().getRoles().stream()
                .map(r -> "ROLE_" + r.getRoleName().name())
                .toArray(String[]::new));
        return builder.build();
    }
}
