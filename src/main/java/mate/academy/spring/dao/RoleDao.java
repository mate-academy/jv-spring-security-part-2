package mate.academy.spring.dao;

import mate.academy.spring.model.Role;

public interface RoleDao {
    Role addRole(Role role);

    Role getByName(String roleName);
}
