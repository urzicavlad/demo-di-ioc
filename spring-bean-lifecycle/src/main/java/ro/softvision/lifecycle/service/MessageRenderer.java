package ro.softvision.lifecycle.service;

public interface MessageRenderer {

    void renderMessage();

    void renderRandomNumber();

    void renderSystemOperation();

    void setMessageProvider(MessageProvider messageProvider);

}
