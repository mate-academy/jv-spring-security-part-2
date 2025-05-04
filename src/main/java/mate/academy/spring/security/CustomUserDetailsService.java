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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        mate.academy.spring.model.User user;
        user = userService.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found by email: " + email));
        User.UserBuilder builder = User.withUsername(email);
        builder.password(user.getPassword());
        builder.roles(user.getRoles()
                .stream()
                .map(x -> x.getRoleName().name())
                .toArray(String[]::new));
        return builder.build();
    }
}
