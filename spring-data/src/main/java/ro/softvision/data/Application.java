package ro.softvision.data;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@ComponentScan("ro.softvision.data")
@EnableTransactionManagement
public class Application {
}
