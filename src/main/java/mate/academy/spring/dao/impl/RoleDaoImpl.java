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
public class RoleDaoImpl<T> extends AbstractDao<Role> implements RoleDao {
    public RoleDaoImpl(SessionFactory factory, Class<Role> roleClass) {
        super(factory, roleClass);
    }

    @Override
    public Optional<Role> getByName(Role.RoleName roleName) {
        try (Session session = factory.openSession()) {
            Query<Role> getRoleByNameQuery = session.createQuery(
                    "from Role r where r.RoleName = :name",
                    Role.class
            );
            getRoleByNameQuery.setParameter("name", roleName);
            return getRoleByNameQuery.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find role with name "
            + roleName, e);
        }
    }
}
