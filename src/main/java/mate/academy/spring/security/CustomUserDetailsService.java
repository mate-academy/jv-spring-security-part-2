package mate.academy.spring.security;

import mate.academy.spring.model.User;
import mate.academy.spring.service.UserService;
import org.springframework.security.core.userdetails.User.UserBuilder;
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
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        User user = userService.findByEmail(userName).orElseThrow(()
                -> new UsernameNotFoundException(
                "The user with login: " + userName + " doesn't exist in DB."));
        UserBuilder builder = org.springframework.security.core.userdetails
                .User.withUsername(userName);
        builder.password(user.getPassword());
        builder.authorities(user.getRoles()
                .stream()
                .map(role -> role.getRole().name())
                .toArray(String[]::new));
        return builder.build();
    }
}
