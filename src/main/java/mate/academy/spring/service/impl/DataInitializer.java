package mate.academy.spring.service.impl;

import java.util.Set;
import javax.annotation.PostConstruct;
import mate.academy.spring.model.Role;
import mate.academy.spring.model.User;
import mate.academy.spring.service.RoleService;
import mate.academy.spring.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class DataInitializer {
    private final UserService userService;
    private final RoleService roleService;

    public DataInitializer(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void inject() {
        Role adminRole = new Role();
        adminRole.setRoleName(Role.RoleName.ADMIN);
        roleService.add(adminRole);
        Role userRole = new Role();
        userRole.setRoleName(Role.RoleName.USER);
        roleService.add(userRole);
        User superUser = new User();
        superUser.setEmail("admin@i.ua");
        superUser.setPassword("admin123");
        superUser.setRoles(Set.of(adminRole));
        userService.add(superUser);
        User regularUser = new User();
        regularUser.setEmail("regular@i.ua");
        regularUser.setPassword("regular321");
        regularUser.setRoles(Set.of(userRole));
        userService.add(regularUser);
    }
}
