package mate.academy.spring.service.impl;

import java.util.Set;
import mate.academy.spring.model.Role;
import mate.academy.spring.model.User;
import mate.academy.spring.service.AuthenticationService;
import mate.academy.spring.service.RoleService;
import mate.academy.spring.service.ShoppingCartService;
import mate.academy.spring.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;
    private final RoleService roleService;
    private final PasswordEncoder encoder;

    public AuthenticationServiceImpl(UserService userService,
             ShoppingCartService shoppingCartService,
             RoleService roleService, PasswordEncoder encoder) {
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
        this.roleService = roleService;
        this.encoder = encoder;
    }

    @Override
    public User register(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(encoder.encode(password));
        user.setRoles(Set.of(roleService.getByName(Role.RoleName.USER)));
        User added = userService.add(user);
        shoppingCartService.registerNewShoppingCart(user);
        return added;
    }
}
