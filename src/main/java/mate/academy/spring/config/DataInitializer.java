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
        Role userRole = new Role();
        userRole.setName(Role.Type.USER);
        Role adminRole = new Role();
        adminRole.setName(Role.Type.ADMIN);
        roleService.add(userRole);
        roleService.add(adminRole);
        User user = new User();
        user.setEmail("dead@gmail.com");
        user.setPassword("1234");
        user.setRoles(Set.of(adminRole));
        userService.add(user);
    }
}
