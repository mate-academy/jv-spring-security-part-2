package mate.academy.spring.service.impl;

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
    private void inject() {
        Role adminRole = new Role();
        adminRole.setRoleName(Role.RoleName.ADMIN);
        Role userRole = new Role();
        userRole.setRoleName(Role.RoleName.USER);
        adminRole = roleService.add(adminRole);
        userRole = roleService.add(userRole);
        User adminUser = new User();
        adminUser.setEmail("admin@i.ua");
        adminUser.setPassword("12345678");
        adminUser.setRoles(Set.of(adminRole));
        userService.add(adminUser);
    }
}
