package mate.academy.spring.service.impl;

import static org.springframework.security.core.userdetails.User.builder;
import static org.springframework.security.core.userdetails.User.withUsername;

import mate.academy.spring.model.Role;
import mate.academy.spring.model.User;
import mate.academy.spring.service.UserService;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found by email: " + email));


        String[] roles = user.getRoles().stream()
                .map(Role::getRoleName)
                .toArray(String[]::new);
        return builder()
                .username(email)
                .password(user.getPassword())
                .authorities(roles)
                .build();
    }
}
