package mate.academy.spring.util;

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

    public DataInitializer(UserService userService,
                           RoleService roleService) {
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
        User adminUser = new User();
        adminUser.setEmail("admin@i.ua");
        adminUser.setPassword("admin123");
        adminUser.setRoles(Set.of(adminRole));
        userService.add(adminUser);
        User simpleUser = new User();
        simpleUser.setEmail("user@i.ua");
        simpleUser.setPassword("11111111");
        simpleUser.setRoles(Set.of(userRole));
        userService.add(simpleUser);
    }
}
