package ro.softvision.lifecycle.service;

public interface MessageProvider {

    String provideMessage();

    Double provideRandomNumber();

    String provideSystemProperties();

    void setMessage(String message);

    void setRandomNumber(double randomNumber);

    void setSystemProperties(String operationSystem);
}
