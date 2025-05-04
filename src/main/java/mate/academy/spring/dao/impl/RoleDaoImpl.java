package mate.academy.spring.dao.impl;

import java.util.Optional;
import mate.academy.spring.dao.RoleDao;
import mate.academy.spring.exception.DataProcessingException;
import mate.academy.spring.model.Role;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl implements RoleDao {
    private SessionFactory factory;

    @Override
    public Role add(Role role) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(role);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add role " + role, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return role;
    }

    @Override
    public Optional<Role> getByName(Role.RoleName roleName) {
        try (Session session = factory.openSession()) {
            Query<Role> query = session.createQuery("FROM Role r "
                    + "WHERE r.name = :roleName", Role.class);
            query.setParameter("roleName", roleName);
            return query.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get role by role name " + roleName, e);
        }
    }
}
