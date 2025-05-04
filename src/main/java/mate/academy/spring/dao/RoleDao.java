package mate.academy.spring.dao;

import static mate.academy.spring.model.Role.RoleName;

import mate.academy.spring.model.Role;

public interface RoleDao {
    Role add(Role role);

    Role getByName(RoleName role);
}
