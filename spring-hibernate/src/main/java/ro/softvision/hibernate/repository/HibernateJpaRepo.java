package ro.softvision.hibernate.repository;

import org.springframework.stereotype.Repository;
import ro.softvision.hibernate.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.util.Optional;
import java.util.Set;

@Repository("hibernateJpaRepo")
public class HibernateJpaRepo implements UserRepo {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> save(User user) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public Optional<User> findByIdUsingNamedParameters(Long id) {
        return Optional.empty();
    }

    @Override
    public Integer countUsers() {
        return null;
    }

    @Override
    public Set<User> findAll() {
        return null;
    }
}
