package mate.academy.spring.security;

import java.util.NoSuchElementException;
import mate.academy.spring.model.User;
import mate.academy.spring.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServiceImp implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsServiceImp(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByEmail(username).orElseThrow(
                () -> new NoSuchElementException("UserNotFoundException"));
        org.springframework.security.core.userdetails.User.UserBuilder userBuilder
                = org.springframework.security.core.userdetails.User.withUsername(username);
        userBuilder.password(user.getPassword());
        userBuilder.roles(user.getRoles()
                .stream().map(r -> r.getRoleName().name()).toArray(String[]::new));
        return userBuilder.build();
    }
}
