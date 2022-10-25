package mate.academy.spring.dao.impl;

import java.util.Optional;
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
    public RoleDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory, Role.class);
    }

    @Override
    public Optional<Role> getByName(String roleName) {
        try (Session session = factory.openSession()) {
            return Optional.of(session.createQuery(
                            "SELECT r FROM Role r WHERE r.roleName = :roleName", Role.class)
                    .setParameter("roleName", RoleName.valueOf(roleName))
                    .getSingleResult());
        } catch (Exception exception) {
            throw new DataProcessingException("Can`t find role: " + roleName, exception);
        }
    }
}
