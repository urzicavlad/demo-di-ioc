package ro.softvision.jdbc.repository;

import ro.softvision.jdbc.entity.User;

import java.util.Optional;
import java.util.Set;

public interface UserRepo {

    Optional<User> save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByIdUsingNamedParameters(Long id);

    Integer countUsers();

    Set<User> findAll();

    void printHtmlAllByName(String name);

}
