package ro.softvision.data.repository;

import ro.softvision.data.entity.User;

import java.util.List;

public interface UserRepo {

    User save(User user);

    List<User> findAll();
}
