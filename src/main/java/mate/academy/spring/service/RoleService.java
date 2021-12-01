package mate.academy.spring.service;

import java.util.Optional;
import mate.academy.spring.model.Role;

public interface RoleService {
    void add(Role role);

    Optional<Role> getRoleByName(String roleName);
}
