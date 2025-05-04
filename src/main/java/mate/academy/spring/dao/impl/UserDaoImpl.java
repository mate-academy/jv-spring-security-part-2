package mate.academy.spring.dao.impl;

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
                    "FROM User WHERE email = :email", User.class);
            findByEmail.setParameter("email", email);
            return findByEmail.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("User with email " + email + " not found", e);
        }
    }

    @Override
    public Optional<User> get(Long id) {
        try (Session session = factory.openSession()) {
            Query<User> getUser = session.createQuery(
                    "FROM User u "
                            + "INNER JOIN FETCH u.roles "
                            + "WHERE u.id = :id", User.class);
            getUser.setParameter("id", id);
            return getUser.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't found user by id " + id, e);
        }
    }
}
