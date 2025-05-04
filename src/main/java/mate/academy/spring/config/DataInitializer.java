package mate.academy.spring.config;

import javax.annotation.PostConstruct;
import mate.academy.spring.model.Role;
import mate.academy.spring.service.AuthenticationService;
import mate.academy.spring.service.RoleService;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    private final RoleService roleService;
    private final AuthenticationService authenticationService;

    public DataInitializer(RoleService roleService, AuthenticationService authenticationService) {
        this.roleService = roleService;
        this.authenticationService = authenticationService;
    }

    @PostConstruct
    public void inject() {
        Role adminRole = new Role();
        adminRole.setRoleName(Role.RoleName.ADMIN);
        roleService.add(adminRole);
        Role userRole = new Role();
        userRole.setRoleName(Role.RoleName.USER);
        roleService.add(userRole);
        authenticationService.register("admin@i.ua", "admin123");
    }
}
