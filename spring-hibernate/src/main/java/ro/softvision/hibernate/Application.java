package ro.softvision.hibernate;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan({"ro.softvision.hibernate", "ro.softvision.hibernate.config"})
@Profile("dev")
@EnableTransactionManagement
public class Application {
}
