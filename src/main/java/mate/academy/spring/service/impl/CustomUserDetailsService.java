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
        Optional<User> byEmail = userService.findByEmail(username);
        if (byEmail.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        User user = byEmail.get();
        UserBuilder builder = org.springframework.security.core.userdetails.User
                .withUsername(username);
        String[] stringsRole = user.getRoleSet().stream()
                .map(Role::getRoleName)
                .toArray(String[]::new);

        return builder.username(username)
                .password(user.getPassword())
                .authorities(stringsRole)
                .build();
    }
}
