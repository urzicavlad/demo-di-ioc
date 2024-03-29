package ro.softvision.lifecycle.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import ro.softvision.lifecycle.service.MessageProvider;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class MessageProviderImpl implements MessageProvider, InitializingBean, DisposableBean{

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageProviderImpl.class);

    private String message;

    private Double randomNumber;

    private String systemProperties;


    @Override
    public String provideMessage() {
        return this.message;
    }

    @Override
    public Double provideRandomNumber() {
        return this.randomNumber;
    }

    @Override
    public String provideSystemProperties(){
        return this.systemProperties;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void setRandomNumber(double randomNumber) {
        this.randomNumber = randomNumber;
    }

    @Override
    public void setSystemProperties(String systemProperties) {
        this.systemProperties = systemProperties;
    }

    @Override
    public void afterPropertiesSet() {
        LOGGER.warn("afterPropertiesSet() method was called!");
    }

    @PostConstruct
    private void postConstructMethod(){
        LOGGER.warn("postConstructMethod() method was called!");
    }

    @Override
    public void destroy() {
        LOGGER.warn("destroy() method was called!");
    }

    @PreDestroy
    private void preDestroyMethod(){
        LOGGER.warn("preDestroyMethod() method was called!");
    }

    private void beanInitMethod(){
        LOGGER.warn("beanInitMethod() method was called!");
    }

    private void beanDestroyMethod(){
        LOGGER.warn("beanDestroyMethod() method was called!");
    }

}
