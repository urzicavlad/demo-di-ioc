package ro.softvision.spring;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.context.support.AbstractApplicationContext;
import ro.softvision.spring.service.Audi;
import ro.softvision.spring.service.AudiSpeedProvider;
import ro.softvision.spring.service.Employee;
import ro.softvision.spring.service.SpeedProvider;

import java.util.Optional;

@Configuration
@PropertySource("application.properties")
@ComponentScan(basePackages = {"ro.softvision.spring"})
public class ApplicationConfig {


    @Value("${audi.speed}")
    private int speed;

    @Bean
    Audi audi() {
        return new Audi();
    }

    @Bean
    SpeedProvider audiSpeedProvider() {
        AudiSpeedProvider audiSpeedProvider = new AudiSpeedProvider();
        audiSpeedProvider.setSpeed(speed);
        return audiSpeedProvider;
    }


    @Bean
    @Primary
    Employee vladEmployee() {
        return new Employee("Vlad");
    }

    @Bean
    Employee danEmployee() {
        return new Employee("Dan");
    }


    public static void main(String[] args) {

        ApplicationContext aplicationContext =
                new AnnotationConfigApplicationContext(ApplicationConfig.class);

    }
}
