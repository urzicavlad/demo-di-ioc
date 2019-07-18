package ro.softvision.data.repository;

import ro.softvision.data.entity.User;

import java.util.Optional;
import java.util.Set;

public interface UserRepo {

  Optional<User> findById(Long id);

  Optional<User> findByIdUsingNamedParameters(Long id);

  Integer countUsers();

  Set<User> findAll();

  void printHtmlAllByName(String name);

}
