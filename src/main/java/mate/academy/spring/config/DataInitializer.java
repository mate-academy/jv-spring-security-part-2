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
    public void inject() {
        roleService.add(new Role(Role.RoleName.ADMIN));
        roleService.add(new Role(Role.RoleName.USER));
        User admin = new User();
        admin.setEmail("admin@god.ua");
        admin.setPassword("1q2w3e4r");
        admin.setRoles(Set.of(roleService.getByName("ADMIN")));
        userService.add(admin);
        User user = new User();
        user.setEmail("user@hobo.ua");
        user.setPassword("12345678");
        user.setRoles(Set.of(roleService.getByName("USER")));
        userService.add(user);
    }
}
