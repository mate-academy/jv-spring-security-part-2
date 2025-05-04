package mate.academy.spring.security;

import static org.springframework.security.core.userdetails.User.withUsername;

import mate.academy.spring.model.User;
import mate.academy.spring.service.UserService;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomerDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail)
            throws UsernameNotFoundException {
        User user = userService.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + userEmail
                        + " not found"));
        UserBuilder userBuilder = withUsername(userEmail);
        userBuilder.password(user.getPassword());
        userBuilder.roles(user.getRoles().stream().map(r -> r.getName().name())
                .toArray(String[]::new));
        return userBuilder.build();
    }
}
