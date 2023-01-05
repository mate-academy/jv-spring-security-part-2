package mate.academy.spring.dao.impl;

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
    public Role getByName(String roleName) {
        try (Session session = factory.openSession()) {
            String sql = String.format(
                    "SELECT * FROM roles WHERE roleName = '%s';",roleName);
            return (Role) session.createSQLQuery(sql).addEntity(Role.class).getSingleResult();
        } catch (Exception e) {
            throw new DataProcessingException("Can`t get role fm db with role name " + roleName, e);
        }
    }
}
