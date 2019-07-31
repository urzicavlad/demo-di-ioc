package ro.softvision.lifecycle.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.softvision.lifecycle.postprocess.beanpostprocess.CustomBeanPostProcessor;
import ro.softvision.lifecycle.service.MessageProvider;
import ro.softvision.lifecycle.service.MessageRenderer;

public class MessageRendererImpl implements MessageRenderer {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomBeanPostProcessor.class);

    private MessageProvider messageProvider;


    @Override
    public void renderMessage() {
        final String message = this.messageProvider.provideMessage();
        LOGGER.info(message);
    }

    @Override
    public void renderRandomNumber() {
        final Double randomNumber = this.messageProvider.provideRandomNumber();
        LOGGER.info("Random number {} ", randomNumber);
    }

    @Override
    public void renderSystemOperation() {
        final String systemOperation = this.messageProvider.provideSystemProperties();
        LOGGER.info("Operation System: {} ", systemOperation);
    }

    @Override
    public void setMessageProvider(MessageProvider messageProvider) {
        this.messageProvider = messageProvider;
    }

}
