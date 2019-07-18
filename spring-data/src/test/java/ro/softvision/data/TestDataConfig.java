package ro.softvision.data;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ro.softvision.data.entity.User;
import ro.softvision.data.repository.UserRepo;

import java.util.Optional;
import java.util.Set;

@ContextConfiguration(classes = {Application.class})
@ActiveProfiles("dev")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestDataConfig {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private UserRepo repo;

    @After
    public void tearDown(){
        ((AbstractApplicationContext)context).registerShutdownHook();
    }


    @Test
    public void testFindByIdPositive() {
        Optional<User> byId = repo.findById(1L);
        Assert.assertTrue(byId.isPresent());
    }

    @Test
    public void testFindByIdNegative() {
        Optional<User> byId = repo.findById(100L);
        Assert.assertFalse(byId.isPresent());
    }

    @Test
    public void testFindByIdUsingNamedParametersPositive() {
        Optional<User> byId = repo.findByIdUsingNamedParameters(1L);
        Assert.assertTrue(byId.isPresent());
    }

    @Test
    public void testFindByIdUsingNamedParametersNegative() {
        Optional<User> byId = repo.findByIdUsingNamedParameters(100L);
        Assert.assertFalse(byId.isPresent());
    }

    @Test
    public void testCountUsers() {
        Integer countResult = repo.countUsers();
        Assert.assertEquals(Integer.valueOf(4), countResult);
    }

    @Test
    public void testFindAll() {
        Set<User> users = repo.findAll();
        Assert.assertFalse(users.isEmpty());
    }

    @Test
    public void testPrintHtmlAllByName() {
        repo.printHtmlAllByName("John");
    }

}
