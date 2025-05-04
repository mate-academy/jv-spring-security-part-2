package mate.academy.spring.service.impl;

import java.util.Set;
import mate.academy.spring.model.Role;
import mate.academy.spring.model.User;
import mate.academy.spring.service.AuthenticationService;
import mate.academy.spring.service.RoleService;
import mate.academy.spring.service.ShoppingCartService;
import mate.academy.spring.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Role.RoleName DEFAULT_ROLE = Role.RoleName.USER;
    private final RoleService roleService;
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;

    public AuthenticationServiceImpl(RoleService roleService,
                                     UserService userService,
                                     ShoppingCartService shoppingCartService) {
        this.roleService = roleService;
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
    }

    @Override
    public User register(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        Role role = roleService.getByName(DEFAULT_ROLE.name());
        user.setRoles(Set.of(role));
        userService.add(user);
        shoppingCartService.registerNewShoppingCart(user);
        return user;
    }
}
