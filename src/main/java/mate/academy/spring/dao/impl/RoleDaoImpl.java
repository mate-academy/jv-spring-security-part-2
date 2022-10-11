package mate.academy.spring.dao.impl;

import mate.academy.spring.dao.AbstractDao;
import mate.academy.spring.dao.RoleDao;
import mate.academy.spring.exception.DataProcessingException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.Optional;
import mate.academy.spring.model.Role;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl extends AbstractDao<Role> implements RoleDao {
    public RoleDaoImpl(SessionFactory factory) {
        super(factory, Role.class);
    }

    @Override
    public Optional<Role> getByName(String roleName) {
        try (Session session = factory.openSession()) {
            return session
                    .createQuery("FROM Role "
                            + "WHERE roleName =:role", Role.class)
                    .setParameter("role",
                            Role.RoleName.valueOf(roleName)).uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Not found role by name: " + roleName, e);
        }
    }
}
