package ro.softvision.datajpa.service;


import ro.softvision.datajpa.entity.User;

import java.util.Set;

public interface UserService {

    User save(User user);

    Set<User> getAll();

    User getById(Long id);
}
