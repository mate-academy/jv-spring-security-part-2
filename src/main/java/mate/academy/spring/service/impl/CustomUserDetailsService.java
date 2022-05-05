package mate.academy.spring.service.impl;

import mate.academy.spring.model.Role;
import mate.academy.spring.model.User;
import mate.academy.spring.service.UserService;
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
    public UserDetails loadUserByUsername(String name)
            throws UsernameNotFoundException {
        User user = userService.findByEmail(name)
                .orElseThrow(() -> new UsernameNotFoundException("Can't get user: " + name));
        org.springframework.security.core.userdetails.User.UserBuilder userBuilder =
                org.springframework.security.core.userdetails.User.withUsername(name);
        userBuilder.password(user.getPassword());
        userBuilder.roles(user.getRoles().stream().map(Role::getRoleName).toArray(String[]::new));
        return userBuilder.build();
    }
}
