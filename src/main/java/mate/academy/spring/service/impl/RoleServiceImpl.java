package mate.academy.spring.service.impl;

import javax.persistence.EntityNotFoundException;
import mate.academy.spring.dao.RoleDao;
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
    public Role getByName(String roleType) {
        return roleDao.getByName(roleType).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format("Can't found role %s ", roleType)));
    }
}
