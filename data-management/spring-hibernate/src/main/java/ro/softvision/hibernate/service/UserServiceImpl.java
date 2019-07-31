package ro.softvision.hibernate.service;

import org.springframework.stereotype.Service;
import ro.softvision.hibernate.entity.User;
import ro.softvision.hibernate.repository.UserRepo;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userHibernateRepo;

    public UserServiceImpl(UserRepo userHibernateRepo) {
        this.userHibernateRepo = userHibernateRepo;
    }

    @Override
    public User save(User user) {
        return userHibernateRepo.save(user).orElse(null);
    }

    @Override
    public Set<User> getAll() {
        return userHibernateRepo.findAll();
    }

    @Override
    public User getById(Long id) {
        return userHibernateRepo.findById(id).orElse(null);
    }
}
