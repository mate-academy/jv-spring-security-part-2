package mate.academy.spring.service.impl;

import java.util.Optional;
import mate.academy.spring.model.User;
import mate.academy.spring.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userService.findByEmail(email);
        org.springframework.security.core.userdetails.User.UserBuilder userBuilder;
        if (userOptional.isPresent()) {
            userBuilder = org.springframework.security.core.userdetails.User.withUsername(email);
            userBuilder.password(userOptional.get().getPassword());
            userBuilder.roles(userOptional.get().getRoles().stream()
                    .map(role -> role.getName().toString())
                    .toArray(String[]::new));
            return userBuilder.build();
        }
        throw new UsernameNotFoundException("User not found");
    }
}
