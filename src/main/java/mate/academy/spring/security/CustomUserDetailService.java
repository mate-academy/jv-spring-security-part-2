package mate.academy.spring.security;

import static org.springframework.security.core.userdetails.User.withUsername;

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
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User with email = " + email + " not found");
        }
        User user = userOptional.get();
        UserBuilder builder = withUsername(email);
        builder.password(user.getPassword());
        String[] roles = user.getRoles().stream().map(Role::getAuthority)
                .toArray(String[]::new);
        if (roles.length > 0) {
            builder.roles(roles);
        }
        return builder.build();
    }
}
