package mate.academy.spring.controller;

import mate.academy.spring.model.Role;
import mate.academy.spring.service.AuthenticationService;
import mate.academy.spring.service.RoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inject")
public class InjectController {
    private final RoleService roleService;
    private final AuthenticationService authenticationService;

    public InjectController(RoleService roleService,
                            AuthenticationService authenticationService) {
        this.roleService = roleService;
        this.authenticationService = authenticationService;
    }

    @GetMapping
    public String injectData() {
        roleService.add(new Role(Role.RoleName.USER));
        roleService.add(new Role(Role.RoleName.ADMIN));
        authenticationService.registerUser("bob@i.ua","1234");
        authenticationService.registerUser("mishel@i.ua","1234");
        return "Done!";
    }
}
