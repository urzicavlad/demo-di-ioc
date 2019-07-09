package ro.softvision.lifecycle;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.softvision.lifecycle.service.MessageRenderer;

public class Application {


    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext("ro.softvision.lifecycle");
        annotationConfigApplicationContext
                .getBean(MessageRenderer.class)
                .render();
        annotationConfigApplicationContext.registerShutdownHook();
    }

}
