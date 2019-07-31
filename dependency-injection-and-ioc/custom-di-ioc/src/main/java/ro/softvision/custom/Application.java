package ro.softvision.custom;

import ro.softvision.custom.service.MessagePrinter;
import ro.softvision.custom.service.MessageProvider;
import ro.softvision.custom.service.impl.HelloWorldPrinter;
import ro.softvision.custom.service.impl.HelloWorldProvider;

import static ro.softvision.custom.container.PropertiesBeanFactory.getInstance;

public class Application {

    public static void main(String[] args) {
        // inversion of control
        MessageProvider messageProvider = getInstance().getBean(HelloWorldProvider.class);
        MessagePrinter messagePrinter = getInstance().getBean(HelloWorldPrinter.class);

        // injecting the dependency
        messagePrinter.setMessageProvider(messageProvider);

        // using the dependency
        messagePrinter.print();
    }
}
