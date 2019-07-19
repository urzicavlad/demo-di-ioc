package ro.softvision.data.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ro.softvision.data.entity.User;
import ro.softvision.data.repository.UserRepo;

@Service
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    @Override
    @Transactional(transactionManager = "additionalTxManager")
    public User getById(Long id) {
        return getUser(id);
    }

    @Transactional(propagation = Propagation.MANDATORY, transactionManager = "additionalTxManager")
    public User getUser(Long id){
        return userRepo.findById(id).orElse(null);
    }
}
