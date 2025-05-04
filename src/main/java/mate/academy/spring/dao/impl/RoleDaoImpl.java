package mate.academy.spring.dao.impl;

import mate.academy.spring.dao.AbstractDao;
import mate.academy.spring.dao.RoleDao;
import mate.academy.spring.exception.DataProcessingException;
import mate.academy.spring.model.Role;
import mate.academy.spring.model.Role.RoleName;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl extends AbstractDao<Role> implements RoleDao {
    public RoleDaoImpl(SessionFactory factory) {
        super(factory, Role.class);
    }

    @Override
    public Role getByName(String roleName) {
        try (Session session = factory.openSession()) {
            RoleName role = RoleName.valueOf(roleName);
            return session.createQuery(
                            "SELECT r FROM Role r WHERE r.roleName = :roleName", Role.class)
                    .setParameter("roleName", role)
                    .getSingleResult();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find role with name: " + roleName, e);
        }
    }
}
