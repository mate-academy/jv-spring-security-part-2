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

    public DataInitializer(RoleService roleService,
                           UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @PostConstruct
    public void inject() {
        Role adminRole = new Role(Role.RoleName.ADMIN);
        Role userRole = new Role(Role.RoleName.USER);
        roleService.add(adminRole);
        roleService.add(userRole);
        User admin = new User();
        admin.setEmail("admin2022@i.ua");
        admin.setPassword("admin2022");
        admin.setRoles(Set.of(adminRole));
        User user = new User();
        user.setEmail("user2022@gmail.com");
        user.setPassword("user2022");
        user.setRoles(Set.of(userRole));
        userService.add(admin);
        userService.add(user);
    }
}
