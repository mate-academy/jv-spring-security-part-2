package mate.academy.spring.service;

import mate.academy.spring.model.Role;
import mate.academy.spring.security.RoleName;

public interface RoleService {
    Role add(Role role);

    Role getByRole(RoleName roleName);
}
