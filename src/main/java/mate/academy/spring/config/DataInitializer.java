package mate.academy.spring.config;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.PostConstruct;
import mate.academy.spring.model.Role;
import mate.academy.spring.model.User;
import mate.academy.spring.service.RoleService;
import mate.academy.spring.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    private static final Role.RoleName ROLE_ADMIN = Role.RoleName.ADMIN;
    private static final String ADMIN_EMAIL = "admin@i.ua";
    private static final String ADMIN_PASSWORD = "admin123";
    private static final Role.RoleName ROLE_USER = Role.RoleName.USER;
    private static final String USER_EMAIL = "user@i.ua";
    private static final String USER_PASSWORD = "user123";
    private final RoleService roleService;
    private final UserService userService;

    public DataInitializer(RoleService roleService,
                           UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @PostConstruct
    public void inject() {
        Role adminRole = new Role();
        adminRole.setRoleName(ROLE_ADMIN);
        roleService.add(adminRole);
        Role userRole = new Role();
        userRole.setRoleName(ROLE_USER);
        roleService.add(userRole);
        User admin = new User();
        admin.setEmail(ADMIN_EMAIL);
        admin.setPassword(ADMIN_PASSWORD);
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        admin.setRoles(adminRoles);
        userService.add(admin);
        User user = new User();
        user.setEmail(USER_EMAIL);
        user.setPassword(USER_PASSWORD);
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);
        user.setRoles(userRoles);
        userService.add(user);
    }
}
