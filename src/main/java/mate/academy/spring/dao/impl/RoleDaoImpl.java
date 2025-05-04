package mate.academy.spring.dao.impl;

import mate.academy.spring.dao.AbstractDao;
import mate.academy.spring.dao.RoleDao;
import mate.academy.spring.exception.DataProcessingException;
import mate.academy.spring.model.Role;
import mate.academy.spring.model.RoleName;
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
    public Role getByName(RoleName roleName) {
        try (Session session = factory.openSession()) {
            Query<Role> getRoleByName = session.createQuery("FROM Role r "
                    + "WHERE r.roleName = :roleName", Role.class);
            getRoleByName.setParameter("roleName", roleName);
            return getRoleByName.getSingleResult();
        } catch (Exception e) {
            throw new DataProcessingException("Couldn't find role by role name: "
                    + roleName, e);
        }
    }
}
