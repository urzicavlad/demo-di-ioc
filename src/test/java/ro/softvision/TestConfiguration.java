package ro.softvision;


import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ro.softvision.spring.ApplicationConfig;
import ro.softvision.spring.service.Car;
import ro.softvision.spring.service.Employee;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(classes = {ApplicationConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("/test-application.properties")
public class TestConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Employee vladEmployee;

    @Autowired
    @Qualifier("danEmployee")
    private Employee danEmployee;

    @Autowired
    private Employee danut;

    @Autowired
    private Car car;

    @Autowired
    private Employee[] employeesArray;

    @Autowired
    private List<Employee> employeeList;

    @Autowired
    private Map<String, Employee> employeeMap;


    @After
    public void shutdownTheContainer() {
        ((ConfigurableApplicationContext) applicationContext).registerShutdownHook();
    }

    @Test
    public void getCarSpeed() {
        assertEquals(100, car.getSpeed());
    }

    @Test
    public void getEmployeeByType() {
        assertEquals("Vlad", vladEmployee.getName());
    }

    @Test
    public void getEmployeeByQualifier() {
        assertEquals("Dan", danEmployee.getName());
    }

    @Test
    public void getAllFromContext() {
        assertEquals(2, employeesArray.length);
        assertEquals(2, employeeList.size());
        assertEquals(2, employeeMap.size());
    }
}
