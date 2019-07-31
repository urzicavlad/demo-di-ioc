package ro.softvision.datajpa.repository;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ro.softvision.datajpa.Application;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@ContextConfiguration(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
public class UserRepoTest {

    @Autowired
    private UserRepo userRepo;


    @Test
    public void testFindAllByUsername() {
        var johnUsersList = userRepo.findAllByUsername("John");
        assertThat(johnUsersList, is(not(empty())));
    }

    @Test
    public void testFindAllByUsernameIsLike() {
        var usersWhoContainsOhinUsernameList = userRepo.findAllByUsernameIsLike("%oh%");
        assertThat(usersWhoContainsOhinUsernameList, is(not(empty())));
    }

    @Test
    public void testCountAll() {
        var numberOfUsers = userRepo.countAll();
        assertThat(numberOfUsers, is(greaterThan(0L)));
    }

    @Test
    public void testFindOneByUsername() {
        var user = userRepo.findOneByUsername("John");
        assertThat(user, is(not(nullValue())));
    }

    @Test
    public void testFindUsernameById() {
        var username = userRepo.findUsernameById(1L);
        assertTrue(username.isPresent());
        assertThat(username.get(), is("John"));
    }
}
