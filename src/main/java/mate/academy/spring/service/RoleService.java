package mate.academy.spring.service;

import mate.academy.spring.model.Role;
import mate.academy.spring.model.RoleName;

public interface RoleService {
    Role add(Role role);

    Role getByName(RoleName roleName);
}
