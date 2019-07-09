package ro.softvision.lifecycle.postprocess.beanfactorypostproecess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
public class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor , Ordered {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomBeanFactoryPostProcessor.class);

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        LOGGER.info("[BEAN FACTORY POST PROCESSOR] called!");
    }

    @Override
    // offer the order between multiple instances of BeanFactoryPostProcessor
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
