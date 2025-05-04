package mate.academy.spring.dao.impl;

import java.util.Optional;
import mate.academy.spring.dao.RoleDao;
import mate.academy.spring.exception.DataProcessingException;
import mate.academy.spring.model.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl implements RoleDao {
    private final SessionFactory factory;

    @Autowired
    public RoleDaoImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Role add(Role role) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(role);
            transaction.commit();
            return role;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert role: "
                    + role, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<Role> getByName(Role.RoleName roleName) {
        try (Session session = factory.openSession()) {
            Query<Role> getByName = session.createQuery(
                    "FROM Role r WHERE r.roleName = :roleName", Role.class);
            getByName.setParameter("roleName", roleName);
            return getByName.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Role with role name " + roleName + " not found", e);
        }
    }
}
