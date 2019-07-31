package ro.softvision.jdbc.repository;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ro.softvision.jdbc.Application;
import ro.softvision.jdbc.entity.User;

import java.util.Optional;
import java.util.Set;

@ContextConfiguration(classes = {Application.class})
@ActiveProfiles("dev")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestDataConfig {

    @Autowired
    private ApplicationContext context;

    @Autowired
    @Qualifier("userTemplateRepo")
    private UserRepo userTemplateRepo;

    @After
    public void tearDown() {
        ((AbstractApplicationContext) context).registerShutdownHook();
    }

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testFindByIdPositive() {
        Optional<User> byId = userTemplateRepo.findById(1L);
        Assert.assertTrue(byId.isPresent());
    }

    @Test
    public void testFindByIdNegative() {
        Optional<User> byId = userTemplateRepo.findById(100L);
        Assert.assertFalse(byId.isPresent());
    }

    @Test
    public void testFindByIdUsingNamedParametersPositive() {
        Optional<User> byId = userTemplateRepo.findByIdUsingNamedParameters(1L);
        Assert.assertTrue(byId.isPresent());
    }

    @Test
    public void testFindByIdUsingNamedParametersNegative() {
        Optional<User> byId = userTemplateRepo.findByIdUsingNamedParameters(100L);
        Assert.assertFalse(byId.isPresent());
    }

    @Test
    @Commit
    @Sql(value = "classpath:db/extra-test-data.sql",
            config = @SqlConfig(dataSource = "dataSource",
                    encoding = "UTF-8",
                    separator = ";", commentPrefix = "--",
                    transactionManager = "defaultTxManager"),
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM P_USER WHERE ID > 4;",
            config =@SqlConfig(
                    dataSource = "dataSource",
                    transactionMode = SqlConfig.TransactionMode.ISOLATED,
                    transactionManager = "defaultTxManager"),
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testCountUsers() {
        Integer countResult = userTemplateRepo.countUsers();
        Assert.assertEquals(Integer.valueOf(8), countResult);
    }

    @Test
    public void testFindAll() {
        Set<User> users = userTemplateRepo.findAll();
        Assert.assertFalse(users.isEmpty());
    }

    @Test
    public void testPrintHtmlAllByName() {
        userTemplateRepo.printHtmlAllByName("John");
    }

}
