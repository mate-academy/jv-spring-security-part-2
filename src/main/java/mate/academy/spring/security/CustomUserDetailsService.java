package mate.academy.spring.security;

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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> optionalUser = userService.findByEmail(email);

        UserBuilder builder;
        if (optionalUser.isPresent()) {
            builder = org.springframework.security.core.userdetails.User.builder()
                    .username(email)
                    .password(optionalUser.get().getPassword())
                    .roles(optionalUser.get().getRoles().stream()
                            .map(role -> role.getName().getName())
                            .toArray(String[]::new));
            return builder.build();
        }
        throw new UsernameNotFoundException("Can't load user with email=" + email);
    }
}
