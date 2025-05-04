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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email)
                 .orElseThrow(() -> new UsernameNotFoundException("User by email"
                         + email + " not found"));
        org.springframework.security.core.userdetails.User.UserBuilder builder =
                org.springframework.security.core.userdetails.User.withUsername(email);
        builder.password(user.getPassword());
        builder.authorities(user.getRoles()
                .stream()
                .map(Role::getName)
                .toArray(String[]::new));
        return builder.build();
    }
}
