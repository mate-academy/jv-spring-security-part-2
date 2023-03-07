package mate.academy.spring.security;

import java.util.Optional;
import mate.academy.spring.model.Role;
import mate.academy.spring.model.User;
import mate.academy.spring.service.UserService;
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
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = userService.findByEmail(userName);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        UserBuilder builder =
                org.springframework.security.core.userdetails.User.withUsername(userName);
        builder.password(user.get().getPassword());
        builder.authorities(user.get().getRoles().stream().map(Role::getRoleName)
                .map(Role.RoleName::getRoleName).toArray(String[]::new));
        return builder.build();
    }
}
