package mate.academy.spring.service;

import mate.academy.spring.model.Role;

public interface RoleService {
    Role add(Role roleName);

    Role getByName(String roleName);
}
