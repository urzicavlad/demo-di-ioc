package ro.softvision.hibernate.repository;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ro.softvision.hibernate.Application;
import ro.softvision.hibernate.entity.User;

import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

@ContextConfiguration(classes = {Application.class})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
public class HibernateUserRepositoryTest {

    @Autowired
    private UserRepo userHibernateRepo;


    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void save() {
    }

    @Test
    public void findById() {
    }

    @Test
    public void findByIdUsingNamedParameters() {
    }

    @Test
    public void countUsers() {
    }

    @Test
    @Transactional
    public void findAll() {
        Set<User> all = userHibernateRepo.findAll();
        Assert.assertEquals(4, all.size());
    }

    @Test
    @Transactional
    public void testFindById() {
        Optional<User> byId = userHibernateRepo.findById(1L);
        Assert.assertTrue(byId.isPresent());
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    public void testSave() {
        userHibernateRepo.save(new User()
                        .setId(10L)
                        .setEmail("test@test.com")
                        .setPassword("pass")
                        .setUsername("userName")
        );
        Optional<User> byId = userHibernateRepo.findById(10L);
        assertTrue(byId.isPresent());
    }
}