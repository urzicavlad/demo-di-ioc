package ro.softvision.lifecycle;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ro.softvision.lifecycle.service.MessageProvider;
import ro.softvision.lifecycle.service.impl.MessageProviderImpl;
import ro.softvision.lifecycle.service.impl.MessageRendererImpl;

@Configuration
@ComponentScan("ro.softvision.lifecycle")
@PropertySource("application.properties")
public class ApplicationConfig {

    @Bean(initMethod = "beanInitMethod", destroyMethod = "beanDestroyMethod")
    MessageProviderImpl messageProvider(@Value("${message.value}") String message){
        MessageProviderImpl messageRenderer = new MessageProviderImpl();
        messageRenderer.setMessage(message);
        return messageRenderer;
    }

    @Bean
    MessageRendererImpl messageRenderer(MessageProvider messageProvider){
        MessageRendererImpl messageRenderer = new MessageRendererImpl();
        messageRenderer.setMessageProvider(messageProvider);
        return messageRenderer;
    }

}
