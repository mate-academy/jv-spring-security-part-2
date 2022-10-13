package mate.academy.spring.service;

import java.util.Optional;
import mate.academy.spring.model.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private UserService userService;

    public CustomUserDetailService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String userName)
            throws UsernameNotFoundException {
        Optional<User> userOptional = userService.findByEmail(userName);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserBuilder builder =
                    org.springframework.security.core.userdetails.User.withUsername(userName);
            builder.password(user.getPassword());
            builder.authorities(user.getRoles().stream()
                    .map(r -> r.getName().name())
                    .toArray(String[]::new));
            return builder.build();

        }
        throw new UsernameNotFoundException("User " + userName + " not found");
    }
}
