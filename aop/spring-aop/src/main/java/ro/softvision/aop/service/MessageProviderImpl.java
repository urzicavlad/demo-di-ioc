package ro.softvision.aop.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:application.properties")
public class MessageProviderImpl implements MessageProvider {


    @Value("#{'${message.value}'.toUpperCase()}")
    private String message;

    @Override
    public String provideMessage() {
        System.out.printf("*** Calling provideMessage()%n");
//        throw new RuntimeException();
        return this.message;
    }

    private String provideTest(){
        System.out.printf("*** Calling provideTest()%n");
        return "test";
    }
}
