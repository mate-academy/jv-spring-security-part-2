package mate.academy.spring.service.mapper;

import mate.academy.spring.model.Role;

public interface RoleService {
    Role add(Role role);

    Role getByName(String roleName);
}
