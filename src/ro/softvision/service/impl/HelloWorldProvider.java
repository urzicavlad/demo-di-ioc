package ro.softvision.service.impl;

import ro.softvision.service.MessageProvider;

public class HelloWorldProvider implements MessageProvider {

    @Override
    public String provide() {
        return "Hello World!";
    }
}
