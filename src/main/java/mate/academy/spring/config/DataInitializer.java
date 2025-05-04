package mate.academy.spring.config;

import java.util.Set;
import javax.annotation.PostConstruct;
import mate.academy.spring.model.Role;
import mate.academy.spring.model.User;
import mate.academy.spring.service.RoleService;
import mate.academy.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
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
        User user = new User();
        user.setEmail("admin@i.ua");
        user.setPassword("admin123");
        user.setRoleSet(Set.of(adminRole));
        userService.add(user);

        User bob = new User();
        bob.setEmail("bob@i.ua");
        bob.setPassword("1234");
        bob.setRoleSet(Set.of(userRole));
        userService.add(bob);
    }
}
