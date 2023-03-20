package mate.academy.spring.dao.impl;

import java.util.Optional;
import mate.academy.spring.dao.AbstractDao;
import mate.academy.spring.dao.RoleDao;
import mate.academy.spring.exception.DataProcessingException;
import mate.academy.spring.model.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl extends AbstractDao<Role> implements RoleDao {
    public RoleDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory, Role.class);
    }

    @Override
    public Optional<Role> getByName(String roleName) {
        Role.RoleName role = Role.RoleName.valueOf(roleName);
        try (Session session = factory.openSession()) {
            Query<Role> query = session.createQuery("FROM Role "
                    + "WHERE roleName = :role", Role.class);
            query.setParameter("role", role);
            return query.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can not find role: " + roleName, e);
        }
    }
}
