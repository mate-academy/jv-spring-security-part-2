package mate.academy.spring.service.impl;

import mate.academy.spring.model.User;
import mate.academy.spring.service.UserService;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User foundUser = userService.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User wasn't found"));
        UserBuilder builder = 
                org.springframework.security.core.userdetails.User.withUsername(username);
        builder.password(foundUser.getPassword());
        builder.roles(foundUser.getRoles().stream()
                .map(r -> r.getRoleName().name())
                .toArray(String[]::new));
        return builder.build();
    }
}
