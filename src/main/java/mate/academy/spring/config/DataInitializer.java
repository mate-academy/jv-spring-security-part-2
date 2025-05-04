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
    private UserService userService;
    private RoleService roleService;

    public DataInitializer(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void inject() {
        Role adminRole = new Role();
        Role userRole = new Role();

        adminRole.setRoleName(Role.RoleName.ADMIN);
        userRole.setRoleName(Role.RoleName.USER);

        roleService.add(adminRole);
        roleService.add(userRole);

        User superUser = new User();

        superUser.setEmail("admin@admin.su");
        superUser.setPassword("qwerty123");
        superUser.setRoles(Set.of(adminRole));

        userService.add(superUser);
    }
}
