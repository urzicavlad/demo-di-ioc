package ro.softvision.spring.lifecycle;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;

import java.util.Arrays;

@Configuration
@ComponentScan("ro.softvision.spring.lifecycle")
public class ApplicationConfig {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
        ((AbstractApplicationContext) context).registerShutdownHook();
    }
}
