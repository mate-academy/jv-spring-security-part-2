package mate.academy.spring.dao.impl;

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
    public Role getByName(String roleName) {
        try (Session session = factory.openSession()) {
            Query<Role> getByRole = session.createQuery(
                    "FROM Role WHERE role = :roleName", Role.class);
            getByRole.setParameter("roleName", Role.RoleType.valueOf(roleName));
            return getByRole.uniqueResult();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find role by: " + roleName, e);
        }
    }
}
