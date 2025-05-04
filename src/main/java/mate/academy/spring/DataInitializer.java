package mate.academy.spring;

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
        Role admin = new Role();
        admin.setName(Role.RoleName.ADMIN);
        roleService.add(admin);
        Role user = new Role();
        user.setName(Role.RoleName.USER);
        roleService.add(user);
        User adminUser = new User();
        adminUser.setEmail("admin@email");
        adminUser.setPassword("admin");
        adminUser.setRoles(Set.of(admin));
        userService.add(adminUser);
    }
}
