package mate.academy.spring.service;

import org.springframework.security.core.userdetails.User;
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
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        mate.academy.spring.model.User user = userService.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("Can't load user by email: " + email));
        User.UserBuilder builder = User.withUsername(email);
        builder.password(user.getPassword());
        builder.authorities(user.getRoles().stream().map(r -> r.getRoleName().name())
                .toArray(String[]::new));
        return builder.build();
    }
}
