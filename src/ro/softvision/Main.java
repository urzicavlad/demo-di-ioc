package ro.softvision;

import ro.softvision.di.PropertiesBeanFactory;
import ro.softvision.service.impl.HelloWorldPrinter;
import ro.softvision.service.impl.HelloWorldProvider;
import ro.softvision.service.MessagePrinter;
import ro.softvision.service.MessageProvider;

public class Main {

    public static void main(String[] args) {
        MessageProvider messageProvider = PropertiesBeanFactory.getInstance().getBean(HelloWorldProvider.class);
        MessagePrinter messagePrinter =  PropertiesBeanFactory.getInstance().getBean(HelloWorldPrinter.class);
        messagePrinter.setMessageProvider(messageProvider);
        messagePrinter.print();
    }
}
