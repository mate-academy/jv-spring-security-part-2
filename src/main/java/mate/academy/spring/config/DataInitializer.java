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

    public DataInitializer(UserService userService,
                           RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void inject() {
        Role roleUser = new Role();
        roleUser.setRole(Role.RoleName.USER);
        roleService.add(roleUser);
        User userBob = new User();
        userBob.setRoles(Set.of(roleUser));
        userBob.setEmail("bob@gmai.com");
        userBob.setPassword("1234");
        userService.add(userBob);

        Role roleAdmin = new Role();
        roleAdmin.setRole(Role.RoleName.ADMIN);
        roleService.add(roleAdmin);
        User adminAlice = new User();
        adminAlice.setRoles(Set.of(roleAdmin));
        adminAlice.setEmail("alice@gmail.com");
        adminAlice.setPassword("4321");
        userService.add(adminAlice);
    }
}
