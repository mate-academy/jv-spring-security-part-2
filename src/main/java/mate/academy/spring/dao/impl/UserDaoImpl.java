package mate.academy.spring.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.spring.dao.AbstractDao;
import mate.academy.spring.dao.UserDao;
import mate.academy.spring.exception.DataProcessingException;
import mate.academy.spring.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends AbstractDao<User> implements UserDao {
    public UserDaoImpl(SessionFactory factory) {
        super(factory, User.class);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (Session session = factory.openSession()) {
            Query<User> findByEmail = session.createQuery(
                    "FROM User u INNER JOIN FETCH u.roles WHERE u.email = :email", User.class);
            findByEmail.setParameter("email", email);
            return findByEmail.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("User with email " + email + " not found", e);
        }
    }

    @Override
    public Optional<User> get(Long id) {
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM User u "
                            + "INNER JOIN FETCH u.roles "
                            + "WHERE u.id = :idParam", User.class)
                    .setParameter("idParam", id)
                    .uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get user with id " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM User u "
                            + "INNER JOIN FETCH u.roles", User.class)
                    .getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all users", e);
        }
    }
}
