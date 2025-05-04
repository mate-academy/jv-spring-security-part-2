package mate.academy.spring.service.impl;

import java.util.Optional;
import mate.academy.spring.dao.RoleDao;
import mate.academy.spring.exception.DataProcessingException;
import mate.academy.spring.model.Role;
import mate.academy.spring.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Role add(Role role) {
        return roleDao.add(role);
    }

    @Override
    public Role getByName(String roleName) {
        Optional<Role> roleOptional = roleDao.getByName(Role.RoleName.valueOf(roleName));
        if (roleOptional.isEmpty()) {
            throw new DataProcessingException("Role with name " + roleName + " not found", null);
        }

        return roleOptional.get();
    }
}
