package mate.academy.spring.security;

import java.util.Optional;
import mate.academy.spring.model.User;
import mate.academy.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userByEmail = userService.findByEmail(email);
        UserBuilder userBuilder;
        if (userByEmail.isPresent()) {
            userBuilder =
                    org.springframework.security.core.userdetails.User.withUsername(email);
            userBuilder.password(userByEmail.get().getPassword());
            userBuilder.roles(userByEmail.get().getRoles()
                    .stream()
                    .map(x -> x.getRoleName().name())
                    .toArray(String[]::new));
            return userBuilder.build();
        }
        throw new UsernameNotFoundException("User not found.");
    }
}
