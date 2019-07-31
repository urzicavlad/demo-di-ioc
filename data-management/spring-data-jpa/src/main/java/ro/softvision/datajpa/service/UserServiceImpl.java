package ro.softvision.datajpa.service;

import org.springframework.stereotype.Service;
import ro.softvision.datajpa.entity.User;
import ro.softvision.datajpa.repository.UserRepo;

import java.util.Collections;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public Set<User> getAll() {
        return Collections.emptySet();
    }

    @Override
    public User getById(Long id) {
        return null;
    }
}
