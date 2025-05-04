package mate.academy.spring.security;

import mate.academy.spring.model.User;
import mate.academy.spring.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User with email: " + email + "not found"));
        return org.springframework.security.core.userdetails.User.withUsername(email)
                .password(user.getPassword())
                .roles(user.getRoles().stream()
                        .map(r -> r.getRoleName().name())
                        .toArray(String[]::new))
                .build();
    }
}
