package mate.academy.spring.security;

import mate.academy.spring.service.UserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
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
        mate.academy.spring.model.User user = userService.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        UserBuilder builder = User.withUsername(username);
        builder.password(user.getPassword());
        builder.authorities(user.getUserRoles().stream()
                .map(r -> r.getRoleName().toString()).toArray(String[]::new));
        return builder.build();
    }
}
