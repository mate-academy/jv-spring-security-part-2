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
    public Optional<Role> getByName(Role.RoleName name) {
        try (Session session = factory.openSession()) {
            Query<Role> getByName = session.createQuery(
                    "SELECT DISTINCT r FROM Role r "
                            + "WHERE r.name = :name", Role.class);
            getByName.setParameter("name", name);
            return getByName.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Not found role by name " + name, e);
        }
    }
}
