package mate.academy.spring.security;

import java.util.Optional;
import mate.academy.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<mate.academy.spring.model.User> user = userService.findByEmail(username);
        if (user.isPresent()) {
            User.UserBuilder userBuilder = User.withUsername(username);
            userBuilder.password(user.get().getPassword());
            userBuilder.roles(user.get()
                    .getRoles()
                    .stream()
                    .map(r -> r.getRoleName().toString())
                    .toArray(String[]::new));
            return userBuilder.build();
        }
        throw new UsernameNotFoundException("Can't find user by username: " + username);
    }
}
