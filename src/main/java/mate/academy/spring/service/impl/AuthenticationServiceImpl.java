package mate.academy.spring.service.impl;

import java.util.HashSet;
import java.util.Set;
import mate.academy.spring.model.Role;
import mate.academy.spring.model.User;
import mate.academy.spring.service.AuthenticationService;
import mate.academy.spring.service.ShoppingCartService;
import mate.academy.spring.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;

    public AuthenticationServiceImpl(UserService userService,
                                     ShoppingCartService shoppingCartService) {
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
    }

    @Override
    public User register(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setRoles(makingRoleSet());
        userService.add(user);
        shoppingCartService.registerNewShoppingCart(user);
        return user;
    }

    private Set<Role> makingRoleSet() {
        Role role = new Role();
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        role.setRole("USER");
        return roleSet;
    }
}
