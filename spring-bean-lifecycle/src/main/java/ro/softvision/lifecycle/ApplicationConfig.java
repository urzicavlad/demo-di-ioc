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
@ComponentScan("ro.softvision.lifecycle")
@PropertySource("application.properties")
@Profile({"dev", "qa"})
public class ApplicationConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfig.class);


    private static final int DEFAULT_NUMBER_TO_MULTIPLY = 50;
    private static final String EXPRESION = "#{T(java.lang.Math).random() * " + DEFAULT_NUMBER_TO_MULTIPLY + "}";
    private static final String OPERATION_SYSTEM = "#{@systemProperties['os.name']}";

    @Bean(initMethod = "beanInitMethod", destroyMethod = "beanDestroyMethod")
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

    @Bean
    // Additional implementation - directly.
    static BeanFactoryPostProcessor beanFactoryPostProcessor() {
        return beanFactory -> LOGGER.info("[DIRECTLY BEAN FACTORY POST PROCESSOR] called!");
    }

    @Bean
    // Additional implementation - directly.
    static BeanPostProcessor beanPostProcessorBeforeInitialization() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                LOGGER.info("[DIRECTLY - POST PROCESS BEFORE] called!");
                return bean;
            }
        };
    }

    @Bean
    // Additional implementation - directly.
    static BeanPostProcessor beanPostProcessorAfterInitialization() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                LOGGER.info("[DIRECTLY - POST PROCESS AFTER] called!");
                return bean;
            }
        };
    }

}
