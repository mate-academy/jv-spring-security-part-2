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
    private UserService userService;

    @Autowired
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) 
            throws UsernameNotFoundException {
        Optional<User> optionalUser = userService.findByEmail(username);
        UserBuilder userBuilder =
                org.springframework.security.core.userdetails.User.withUsername(username);
        if (optionalUser.isPresent()) {
            userBuilder.password(optionalUser.get().getPassword());
            userBuilder.roles(optionalUser.get().getRoles()
                    .stream()
                    .map(u -> u.getName().name())
                    .toArray(String[]::new));
            return userBuilder.build();
        }
        throw new UsernameNotFoundException(String.format("User not found with name %s", username));
    }
}
