package mate.academy.spring.service;

import java.util.List;
import mate.academy.spring.model.Role;

public interface RoleService {
    Role add(Role role);

    Role getByName(String roleName);

    List<Role> getAll();
}
