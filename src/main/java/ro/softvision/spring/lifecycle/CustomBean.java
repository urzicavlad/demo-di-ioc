package ro.softvision.spring.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CustomBean implements InitializingBean, DisposableBean {

    private Logger logger = LoggerFactory.getLogger(CustomBean.class);

    @Override
    public void afterPropertiesSet() {
        logger.info("[INIT] METHOD IS CALLED RIGHT NOW!");
    }

    @Override
    public void destroy() {
        logger.info("[DESTROY] METHOD IS CALLED RIGHT NOW!");
    }

}
