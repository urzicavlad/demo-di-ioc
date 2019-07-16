package ro.softvision.aop;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import ro.softvision.aop.service.MessageProvider;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {"ro.softvision.aop.service", "ro.softvision.aop.aspects"} )
@EnableAspectJAutoProxy
public class Application {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        MessageProvider provider = context.getBean(MessageProvider.class);
        String s = provider.provideMessage();
        System.out.println("s = " + s);

    }
}
