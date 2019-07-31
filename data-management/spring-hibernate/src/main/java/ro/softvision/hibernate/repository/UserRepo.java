package ro.softvision.hibernate.repository;

import ro.softvision.hibernate.entity.User;

import java.util.Optional;
import java.util.Set;

public interface UserRepo {

    Optional<User> save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByIdUsingNamedParameters(Long id);

    Integer countUsers();

    Set<User> findAll();

}
