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

        Role userRole = new Role();
        userRole.setRoleName(Role.RoleName.USER);

        roleService.add(adminRole);
        roleService.add(userRole);

        User stepan = new User();
        stepan.setEmail("stepan@i.ua");
        stepan.setPassword("1234");
        stepan.setRoles(Set.of(adminRole));

        User olena = new User();
        olena.setEmail("olena@ukr.net");
        olena.setPassword("abcd");
        olena.setRoles(Set.of(userRole));

        userService.add(stepan);
        userService.add(olena);
    }
}
