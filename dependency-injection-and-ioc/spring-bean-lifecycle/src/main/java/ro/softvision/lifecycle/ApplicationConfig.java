package ro.softvision.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.*;
import ro.softvision.lifecycle.service.MessageProvider;
import ro.softvision.lifecycle.service.impl.MessageProviderImpl;
import ro.softvision.lifecycle.service.impl.MessageRendererImpl;

@Configuration
@Import(BeanPostProcessorConfig.class)
@ComponentScan("ro.softvision.lifecycle")
@PropertySource("application.properties")
@Profile({"dev", "qa"})
@Description("Provides a basic example of a bean class configuration")
public class ApplicationConfig {

    private static final int DEFAULT_NUMBER_TO_MULTIPLY = 50;
    private static final String EXPRESION = "#{T(java.lang.Math).random() * " + DEFAULT_NUMBER_TO_MULTIPLY + "}";
    private static final String OPERATION_SYSTEM = "#{@systemProperties['os.name']}";

    @Bean(initMethod = "beanInitMethod", destroyMethod = "beanDestroyMethod")
    @Description("Provides a basic example of a bean")
    MessageProviderImpl messageProvider(@Value("${message.value}") String message,
                                        @Value(EXPRESION) Double randomNumber,
                                        @Value(OPERATION_SYSTEM) String systemProperties) {
        MessageProviderImpl messageRenderer = new MessageProviderImpl();
        messageRenderer.setMessage(message);
        messageRenderer.setRandomNumber(randomNumber);
        messageRenderer.setSystemProperties(systemProperties);
        return messageRenderer;
    }

    @Bean
    @Lazy
    MessageRendererImpl messageRenderer(MessageProvider messageProvider) {
        MessageRendererImpl messageRenderer = new MessageRendererImpl();
        messageRenderer.setMessageProvider(messageProvider);
        return messageRenderer;
    }


}
