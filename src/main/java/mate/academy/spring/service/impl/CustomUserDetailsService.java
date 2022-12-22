package mate.academy.spring.service.impl;

import mate.academy.spring.service.UserService;
import org.springframework.security.core.userdetails.User;
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
        mate.academy.spring.model.User user = userService.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("Couldn't find user by email"));
        User.UserBuilder builder = User.withUsername(email)
                .password(user.getPassword())
                .roles(user.getRoles()
                        .stream()
                        .map(u -> u.getRoleName().name())
                        .toArray(String[]::new));
        return builder.build();
    }
}
