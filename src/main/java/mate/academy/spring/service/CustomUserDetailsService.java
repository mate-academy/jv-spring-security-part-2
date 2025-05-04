package mate.academy.spring.service;

import static org.springframework.security.core.userdetails.User.withUsername;

import mate.academy.spring.model.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService service;

    public CustomUserDetailsService(UserService service) {
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        User user = service.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("Can't load user by email: " + email));
        UserBuilder builder = withUsername(user.getEmail());
        builder.password(user.getPassword());
        builder.authorities(user.getRoles().stream()
                .map(r -> r.getRoleName().name())
                .toArray(String[]::new));
        return builder.build();
    }
}
