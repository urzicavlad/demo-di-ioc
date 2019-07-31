package ro.softvision.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanPostProcessorConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeanPostProcessorConfig.class);


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
