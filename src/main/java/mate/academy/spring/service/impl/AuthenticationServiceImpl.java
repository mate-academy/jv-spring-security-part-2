package mate.academy.spring.service.impl;

import java.util.Set;
import java.util.stream.Collectors;
import mate.academy.spring.model.Role;
import mate.academy.spring.model.User;
import mate.academy.spring.service.AuthenticationService;
import mate.academy.spring.service.RoleService;
import mate.academy.spring.service.ShoppingCartService;
import mate.academy.spring.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final RoleService roleService;
    private final ShoppingCartService shoppingCartService;

    public AuthenticationServiceImpl(UserService userService,
                                     RoleService roleService,
                                     ShoppingCartService shoppingCartService) {
        this.userService = userService;
        this.roleService = roleService;
        this.shoppingCartService = shoppingCartService;
    }

    @Override
    public User register(String email, String password, Set<String> roles) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        Set<Role> roleSet = roles.stream()
                .map(roleService::getByName)
                .collect(Collectors.toSet());
        user.setRoles(roleSet);
        userService.add(user);
        shoppingCartService.registerNewShoppingCart(user);
        return user;
    }
}
