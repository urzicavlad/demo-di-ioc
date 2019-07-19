package ro.softvision.hibernate.service;

import ro.softvision.hibernate.entity.User;

import java.util.Set;

public interface UserService {

    User save(User user);

    Set<User> getAll();

    User getById(Long id);
}
