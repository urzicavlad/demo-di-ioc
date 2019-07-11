package ro.softvision.lifecycle;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import ro.softvision.lifecycle.service.MessageRenderer;

public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext("ro.softvision.lifecycle");
        annotationConfigApplicationContext.getEnvironment().addActiveProfile("dev");
        annotationConfigApplicationContext
                .getBean(MessageRenderer.class)
                .renderMessage();
        annotationConfigApplicationContext.registerShutdownHook();
    }

}
