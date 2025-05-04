package mate.academy.spring.dao;

import java.util.Optional;
import mate.academy.spring.model.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao {
    Role add(Role role);

    Optional<Role> getByName(String roleName);
}
