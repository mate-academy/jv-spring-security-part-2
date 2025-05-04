package mate.academy.spring.service;

import mate.academy.spring.model.Role;

public interface RoleService {
    Role add(Role role);

    Role get(Long id);

    Role getByName(String name);
}
