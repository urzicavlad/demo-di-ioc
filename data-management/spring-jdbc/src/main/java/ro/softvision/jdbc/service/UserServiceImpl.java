package ro.softvision.jdbc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ro.softvision.jdbc.entity.User;
import ro.softvision.jdbc.repository.UserRepo;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private UserRepo hibernateUserRepo;

    public UserServiceImpl(UserRepo hibernateUserRepo) {
        this.hibernateUserRepo = hibernateUserRepo;
    }


    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public User save(User user) {
        User newUser = new User().setEmail(user.getEmail().replace("@","annotation"))
                .setId(user.getId()+1)
                .setPassword(user.getPassword())
                .setUsername(user.getUsername()+"_DEFAULT");
         hibernateUserRepo.save(user);
         hibernateUserRepo.save(newUser);
         return newUser;
    }

    @Override
    @Transactional(transactionManager = "additionalTxManager")
    public User getById(Long id) {
        return getUser(id);
    }

    @Transactional(propagation = Propagation.MANDATORY, transactionManager = "additionalTxManager")
    public User getUser(Long id){
        return hibernateUserRepo.findById(id).orElse(null);
    }

    @Override
    public Set<User> getAll() {
        return hibernateUserRepo.findAll();
    }
}
