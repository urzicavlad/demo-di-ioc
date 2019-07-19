package ro.softvision.data.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ro.softvision.data.Application;
import ro.softvision.data.entity.User;

@ContextConfiguration(classes = {Application.class})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
public class UserServiceImplTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
        ((AbstractApplicationContext) context).registerShutdownHook();
    }

    @Test
    public void getById() {
        User byId = userService.getById(10L);
        System.out.println(byId);
        Assert.assertNotNull(byId);
    }
}