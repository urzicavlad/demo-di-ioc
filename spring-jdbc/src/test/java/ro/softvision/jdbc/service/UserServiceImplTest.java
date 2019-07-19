package ro.softvision.jdbc.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;
import ro.softvision.jdbc.Application;
import ro.softvision.jdbc.entity.User;

import java.util.Set;

@ContextConfiguration(classes = {Application.class})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
public class UserServiceImplTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private UserService userService;


    @After
    public void tearDown()  {
        ((AbstractApplicationContext) context).registerShutdownHook();
    }

    @Test
    public void getById() {
        User byId = userService.getById(1L);
        System.out.println(byId);
        Assert.assertNotNull(byId);
    }

    @BeforeTransaction
    public void testBeforeTx(){
        System.out.println("THIS PARTICULAR CODE WILL BE EXECUTED *** BEFORE ***  THE TRANSACTION TO BE CREATED!");
    }


    @AfterTransaction
    public void testAfterTx(){
        System.out.println("THIS PARTICULAR CODE WILL BE EXECUTED *** AFTER *** THE TRANSACTION WAS COMMITED!");
    }

    @Test(expected = RuntimeException.class)
    @Transactional(transactionManager = "defaultTxManager")
    @Rollback
    public void testSaveUser() {
        User user1 = new User()
                .setPassword("pasword")
                .setEmail("urzica.vlad@yahoo.com")
                .setId(100L)
                .setUsername("urzica.vlad");
        userService.save(user1);
        Assert.assertNull(userService.getById(100L));
    }

    @Test
    public void testGetAll() {
        Set<User> all = userService.getAll();
        System.out.println(all.size());
    }
}