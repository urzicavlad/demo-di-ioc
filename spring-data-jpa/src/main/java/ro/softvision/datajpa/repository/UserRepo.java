package ro.softvision.datajpa.repository;


import org.springframework.data.repository.CrudRepository;
import ro.softvision.datajpa.entity.User;

import java.util.List;

public interface UserRepo extends CrudRepository<User,Long> {

    List<User> findAllByUsername(String username);
}
