package mate.academy.spring.config;

import java.util.Set;
import javax.annotation.PostConstruct;
import mate.academy.spring.model.Role;
import mate.academy.spring.model.Role.RoleName;
import mate.academy.spring.model.User;
import mate.academy.spring.service.RoleService;
import mate.academy.spring.service.ShoppingCartService;
import mate.academy.spring.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    private final RoleService roleService;
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;

    public DataInitializer(RoleService roleService,
                           ShoppingCartService shoppingCartService,
                           UserService userService) {
        this.roleService = roleService;
        this.shoppingCartService = shoppingCartService;
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

        User testAdmin = new User();
        testAdmin.setEmail("testadmin@gmail.com");
        testAdmin.setPassword("123456789");
        testAdmin.setRoles(Set.of(adminRole));
        userService.add(testAdmin);

        User testUser = new User();
        testUser.setEmail("testuser@gmail.com");
        testUser.setPassword("987654321");
        testUser.setRoles(Set.of(userRole));
        userService.add(testUser);
        shoppingCartService.registerNewShoppingCart(testUser);
    }
}
