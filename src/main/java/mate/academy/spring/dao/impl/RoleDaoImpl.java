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

    public Optional<Role> getByName(String roleName) {
        try (Session session = factory.openSession()) {
            Query<Role> query = session.createQuery(
                    "FROM Role r WHERE r.roleName = :roleName", Role.class);
            query.setParameter("roleName", Role.RoleName.valueOf(roleName));
            return query.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Role by name "
                    + roleName + " not found", e);
        }
    }
}
