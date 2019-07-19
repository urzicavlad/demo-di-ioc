package ro.softvision.hibernate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.softvision.hibernate.entity.User;
import ro.softvision.hibernate.repository.UserRepo;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User save(User user) {
        return userRepo.save(user).orElse(null);
    }

    @Override
    public Set<User> getAll() {
        return userRepo.findAll();
    }

    @Override
    public User getById(Long id) {
        return userRepo.findById(id).orElse(null);
    }
}
