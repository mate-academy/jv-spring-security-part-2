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
    public String inject() {
        Role adminRole = new Role();
        adminRole.setName(Role.RoleName.ADMIN);
        roleService.add(adminRole);
        User admin = new User();
        admin.setEmail("eugen@gmail.com");
        admin.setPassword("1234");
        admin.setRoles(Set.of(adminRole));
        userService.add(admin);

        Role userRole = new Role();
        userRole.setName(Role.RoleName.USER);
        roleService.add(userRole);
        User user = new User();
        user.setEmail("NEeugen@gmail.com");
        user.setPassword("1234");
        user.setRoles(Set.of(userRole));
        userService.add(user);
        return "Success";
    }
}
