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
    public RoleDaoImpl(SessionFactory factory, Class<Role> clazz) {
        super(factory, clazz);
    }

    @Override
    public Optional<Role> get(String roleName) {
        try (Session session = factory.openSession()) {
            Query<Role> findRoleName = session.createQuery(
                    "FROM Role WHERE name = :name", Role.class);
            findRoleName.setParameter("name", roleName);
            return findRoleName.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Role with name " + roleName + " not found", e);
        }
    }
}
