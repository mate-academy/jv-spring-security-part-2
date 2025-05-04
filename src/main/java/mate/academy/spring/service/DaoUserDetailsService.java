package mate.academy.spring.service;

import java.util.Optional;
import java.util.Set;
import mate.academy.spring.model.Role;
import mate.academy.spring.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DaoUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public DaoUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isPresent()) {
            return buildUserDetails(userOptional.get());
        }
        throw new UsernameNotFoundException("Can't find user with email " + email);
    }

    private UserDetails buildUserDetails(User user) {
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(getRolesArray(user.getRoles()))
                .build();
    }

    private String[] getRolesArray(Set<Role> roles) {
        return roles.stream()
                .map(Role::getRoleName)
                .map(Role.RoleEnum::name)
                .toArray(String[]::new);
    }
}
