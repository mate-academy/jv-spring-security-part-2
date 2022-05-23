package mate.academy.spring.service.impl;

import java.util.Optional;
import mate.academy.spring.model.User;
import mate.academy.spring.service.UserService;
import org.springframework.security.core.userdetails.User.UserBuilder;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userService.findByEmail(username);
        UserBuilder builder;
        if (userOptional.isPresent()) {
            builder = org.springframework.security.core.userdetails.User.withUsername(username);
            builder.password(userOptional.get().getPassword());
            builder.authorities(userOptional.get().getRoles().stream()
                    .map(r -> r.getRoleName().name())
                    .toArray(String[]::new));
            return builder.build();
        }
        throw new UsernameNotFoundException("User not found " + username);
    }
}
