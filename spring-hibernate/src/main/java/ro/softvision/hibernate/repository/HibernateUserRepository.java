package ro.softvision.hibernate.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ro.softvision.hibernate.entity.User;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository("userHibernateRepo")
public class HibernateUserRepository implements UserRepo {

    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateUserRepository.class);

    private final SessionFactory sessionFactory;

    public HibernateUserRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Optional<User> save(User user) {
         session().saveOrUpdate(user);
         return Optional.of(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(session().get(User.class, id));
    }

    @Override
    public Optional<User> findByIdUsingNamedParameters(Long id) {
        return null;
    }

    @Override
    public Integer countUsers() {
        return null;
    }

    @Override
    public Set<User> findAll() {
        List users = session().createSQLQuery("SELECT * FROM P_USER").list();
        return new HashSet<>(users);
    }
}
