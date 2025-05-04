package mate.academy.spring.service.impl;

import java.util.Optional;
import mate.academy.spring.model.Role;
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
    private final UserService userService;

    @Autowired
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userService.findByEmail(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            UserBuilder userBuilder =
                    org.springframework.security.core.userdetails.User.withUsername(username);
            userBuilder.password(user.getPassword());
            userBuilder.authorities(user.getRoles()
                    .stream()
                    .map(Role::getRoleName)
                    .map(Role.RoleName::name)
                    .toArray(String[]::new));
            return userBuilder.build();
        }
        throw new UsernameNotFoundException(username + " not found");
    }
}
