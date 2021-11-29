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
    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public DataInitializer(RoleService roleService,
                           UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @PostConstruct
    public void inject() {
        Role adminRole = new Role();
        adminRole.setRoleName(RoleName.ADMIN);
        roleService.add(adminRole);
        Role userRole = new Role();
        userRole.setRoleName(RoleName.USER);
        roleService.add(userRole);

        User user = new User();
        user.setEmail("shrek@boloto.com");
        user.setPassword("qwerty01");
        user.setRoles(Set.of(adminRole));
        userService.add(user);

        User user2 = new User();
        user2.setEmail("donkey@boloto.com");
        user2.setPassword("qwerty02");
        user2.setRoles(Set.of(userRole));
        userService.add(user2);
    }
}
