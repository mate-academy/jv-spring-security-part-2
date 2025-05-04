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
    private final RoleService roleService;
    private final UserService userService;

    public DataInitializer(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @PostConstruct
    public void inject() {
        Role adminRole = new Role();
        adminRole.setRoleName(Role.RoleName.ADMIN);
        roleService.add(adminRole);
        Role userRole = new Role();
        userRole.setRoleName(Role.RoleName.USER);
        roleService.add(userRole);
        User bobAdmin = new User();
        bobAdmin.setEmail("bob@i.ua");
        bobAdmin.setPassword("admin123");
        bobAdmin.setRoles(Set.of(adminRole));
        userService.add(bobAdmin);
        User aliceUser = new User();
        aliceUser.setEmail("alice@i.ua");
        aliceUser.setPassword("user123");
        aliceUser.setRoles(Set.of(userRole));
        userService.add(aliceUser);
    }
}
