package mate.academy.spring.config;

import java.util.Set;
import javax.annotation.PostConstruct;
import mate.academy.spring.model.Role;
import mate.academy.spring.model.RoleName;
import mate.academy.spring.model.User;
import mate.academy.spring.service.RoleService;
import mate.academy.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void inject() {
        Role adminRole = new Role();
        adminRole.setRoleName(RoleName.ADMIN);
        roleService.add(adminRole);
        Role userRole = new Role();
        userRole.setRoleName(RoleName.USER);
        roleService.add(userRole);
        User user = new User();
        user.setEmail("admin@i.ua");
        user.setPassword("admin123");
        user.setRoles(Set.of(adminRole));
        userService.add(user);
    }
}
