package ro.softvision.jdbc.service;

import ro.softvision.jdbc.entity.User;

import java.util.Set;

public interface UserService {

    User getById(Long id);

    User save(User user);

    Set<User> getAll();
}
