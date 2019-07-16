package ro.softvision.data;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import ro.softvision.data.config.DataSourceConfig;
import ro.softvision.data.entity.User;
import ro.softvision.data.repository.UserRepo;

import java.sql.SQLException;
import java.util.List;


@Configuration
@ComponentScan("ro.softvision.data")
public class Application {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);

        UserRepo re = (UserRepo) context.getBean("userTemplateRepo");
        List<User> all = re.findAll();
        System.out.println(all);
    }
}
