package mate.academy.spring.dao.impl;

import java.util.Optional;
import mate.academy.spring.dao.AbstractDao;
import mate.academy.spring.dao.RoleDao;
import mate.academy.spring.exception.DataProcessingException;
import mate.academy.spring.model.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl extends AbstractDao<Role> implements RoleDao {
    public RoleDaoImpl(SessionFactory factory) {
        super(factory, Role.class);
    }

    @Override
    public Optional<Role> getByName(String role) {
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Role r " + "WHERE r.roleName = :role", Role.class)
                    .setParameter("roleName", Role.RoleName.valueOf(role))
                    .uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get Role by name " + role, e);
        }
    }
}
