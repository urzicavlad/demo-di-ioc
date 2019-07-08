package ro.softvision.spring.lifecycle;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class CustomPostProcessor implements BeanPostProcessor {

    private Logger logger = LoggerFactory.getLogger(CustomPostProcessor.class);

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        logger.info("Do some stuff [BEFORE] with bean: {}" , beanName);
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        logger.info("Do some stuff [AFTER] with bean: {}" , beanName);
        return bean;
    }
}
