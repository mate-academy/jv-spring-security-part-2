package mate.academy.spring.security;

import java.util.Set;
import javax.annotation.PostConstruct;
import mate.academy.spring.model.Role;
import mate.academy.spring.model.RoleType;
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
        adminRole.setName(RoleType.ADMIN);
        roleService.add(adminRole);
        Role userRole = new Role();
        userRole.setName(RoleType.USER);
        roleService.add(userRole);
        User user = new User();
        user.setEmail("admin@gmail.com");
        user.setPassword("12345");
        user.setRoles(Set.of(adminRole));
        userService.add(user);
    }
}
