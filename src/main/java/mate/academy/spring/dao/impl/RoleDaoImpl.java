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
    public RoleDaoImpl(SessionFactory factory) {
        super(factory, Role.class);
    }

    @Override
    public Optional<Role> getByName(String roleName) {
        try (Session session = factory.openSession()) {
            Query<Role> getByNameQuery
                    = session.createQuery("FROM Role WHERE roleName = :roleNane", Role.class);
            getByNameQuery.setParameter("roleNane", Role.RoleName.valueOf(roleName));
            return getByNameQuery.uniqueResultOptional();
        } catch (RuntimeException e) {
            throw new DataProcessingException("Can't get role by roleName: " + roleName, e);
        }
    }
}
