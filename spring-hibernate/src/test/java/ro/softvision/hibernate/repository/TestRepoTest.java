package ro.softvision.hibernate.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ro.softvision.hibernate.Application;
import ro.softvision.hibernate.entity.User;

import java.util.Optional;

import static org.junit.Assert.*;

@ContextConfiguration(classes = {Application.class})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
public class TestRepoTest {

    @Autowired
//    @Qualifier("hibernateJpaRepo")
    private UserRepo hibernateJpaRepo;

    @Test
    public void save() {
    }

    @Test
    public void findById() {
        Optional<User> byId = hibernateJpaRepo.findById(1L);
        Assert.assertTrue(byId.isPresent());
    }

    @Test
    public void findByIdUsingNamedParameters() {
    }

    @Test
    public void countUsers() {
    }

    @Test
    public void findAll() {
    }
}