package mate.academy.spring.dao;

import static mate.academy.spring.model.Role.RoleName;

import java.util.Optional;
import mate.academy.spring.model.Role;

public interface RoleDao {
    Role add(Role role);

    Optional<Role> getByName(RoleName roleName);
}
