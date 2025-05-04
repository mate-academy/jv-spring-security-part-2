package mate.academy.spring.config;

import java.util.Set;
import javax.annotation.PostConstruct;
import mate.academy.spring.model.Role;
import mate.academy.spring.model.User;
import mate.academy.spring.service.RoleService;
import mate.academy.spring.service.ShoppingCartService;
import mate.academy.spring.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    private final UserService userService;
    private final RoleService roleService;
    private final ShoppingCartService shoppingCartService;

    public DataInitializer(UserService userService,
                           RoleService roleService,
                           ShoppingCartService shoppingCartService) {
        this.userService = userService;
        this.roleService = roleService;
        this.shoppingCartService = shoppingCartService;
    }

    @PostConstruct
    public void inject() {
        Role adminRole = new Role();
        adminRole.setRoleName(Role.RoleName.ADMIN);
        roleService.add(adminRole);
        Role userRole = new Role();
        userRole.setRoleName(Role.RoleName.USER);
        roleService.add(userRole);

        User admin = new User();
        admin.setEmail("admin@i.ua");
        admin.setPassword("1234");
        admin.setRoles(Set.of(adminRole, userRole));
        userService.add(admin);
        shoppingCartService.registerNewShoppingCart(admin);

        User defaulrUser = new User();
        defaulrUser.setEmail("defaulrUser@i.ua");
        defaulrUser.setPassword("1234");
        defaulrUser.setRoles(Set.of(userRole));
        userService.add(defaulrUser);
        shoppingCartService.registerNewShoppingCart(defaulrUser);

        User adminOnly = new User();
        adminOnly.setEmail("admin@gmail.com");
        adminOnly.setPassword("12344321");
        adminOnly.setRoles(Set.of(adminRole));
        userService.add(adminOnly);
        shoppingCartService.registerNewShoppingCart(adminOnly);
    }
}
