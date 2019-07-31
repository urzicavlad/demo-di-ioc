package ro.softvision.custom.service.impl;

import ro.softvision.custom.service.MessagePrinter;
import ro.softvision.custom.service.MessageProvider;

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
