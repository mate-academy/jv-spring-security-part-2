package mate.academy.spring.config;

import java.util.Set;
import javax.annotation.PostConstruct;
import mate.academy.spring.model.Role;
import mate.academy.spring.model.User;
import mate.academy.spring.service.RoleService;
import mate.academy.spring.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    private final UserService userService;
    private final RoleService roleService;

    public DataInitializer(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void injectData() {
        roleService.add(new Role(Role.RoleName.ADMIN));
        roleService.add(new Role(Role.RoleName.USER));
        User admin = new User();
        admin.setEmail("adminLox@example.com");
        admin.setPassword("12345678");
        admin.setRoles(Set.of(roleService.getByName("ADMIN")));
        User user = new User();
        user.setEmail("user@example.com");
        user.setPassword("12345678");
        user.setRoles(Set.of(roleService.getByName("USER")));
        userService.add(admin);
        userService.add(user);

    }
}
