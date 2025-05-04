package mate.academy.spring.service.impl;

import static org.springframework.security.core.userdetails.User.builder;

import mate.academy.spring.model.User;
import mate.academy.spring.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userService.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("Can't find user with email: "
                        + username));
        String[] roles = user.getRoles()
                .stream()
                .map(r -> r.getRoleName().name())
                .toArray(String[]::new);
        return builder()
                .username(username)
                .password(user.getPassword())
                .roles(roles)
                .build();
    }
}
