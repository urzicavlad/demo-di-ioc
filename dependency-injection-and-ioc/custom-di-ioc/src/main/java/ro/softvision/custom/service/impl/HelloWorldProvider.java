package ro.softvision.custom.service.impl;

import ro.softvision.custom.service.MessageProvider;

public class HelloWorldProvider implements MessageProvider {

    @Override
    public String provide() {
        return "Hello World!";
    }
}
