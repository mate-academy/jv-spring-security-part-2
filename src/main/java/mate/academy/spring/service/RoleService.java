package mate.academy.spring.service;

import mate.academy.spring.model.Role;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    Role add(Role role);

    Role getByName(Role.RoleName roleName);
}
