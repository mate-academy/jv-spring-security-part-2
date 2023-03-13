package mate.academy.spring.config;

import java.util.Set;
import javax.annotation.PostConstruct;
import mate.academy.spring.model.Role;
import mate.academy.spring.model.User;
import mate.academy.spring.service.RoleService;
import mate.academy.spring.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder encoder;

    public DataInitializer(RoleService roleService,
                           UserService userService, PasswordEncoder encoder) {
        this.roleService = roleService;
        this.userService = userService;
        this.encoder = encoder;
    }

    @PostConstruct
    public void inject() {
        Role adminRole = new Role();
        adminRole.setName(Role.RoleName.ADMIN);
        roleService.add(adminRole);
        Role userRole = new Role();
        userRole.setName(Role.RoleName.USER);
        roleService.add(userRole);
        User userAlice = new User();
        userAlice.setEmail("adminalice@gmail.com");
        userAlice.setPassword(encoder.encode("admin123"));
        userAlice.setRoles(Set.of(adminRole));
        userService.add(userAlice);
        User userBob = new User();
        userBob.setEmail("userbob@gmail.com");
        userBob.setPassword(encoder.encode("user123"));
        userBob.setRoles(Set.of(userRole));
        userService.add(userBob);
    }
}
