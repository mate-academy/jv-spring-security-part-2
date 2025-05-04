package mate.academy.spring.security;

import mate.academy.spring.service.UserService;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        mate.academy.spring.model.User user = userService.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User is not found"));
        User.UserBuilder builder = User.withUsername(username);
        builder.password(user.getPassword());
        builder.authorities(user.getRoles()
                .stream()
                .map(r -> r.getRoleName().name())
                .toArray(String[]::new));
        return builder.build();
    }
}
