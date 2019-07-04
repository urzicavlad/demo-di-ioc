package ro.softvision.service.impl;

import ro.softvision.service.MessagePrinter;
import ro.softvision.service.MessageProvider;

public class HelloWorldPrinter implements MessagePrinter {

    private MessageProvider messageProvider;

    @Override
    public void print() {
        System.out.println(messageProvider.provide());
    }

    @Override
    public void setMessageProvider(MessageProvider messageProvider) {
        this.messageProvider = messageProvider;
    }


}
