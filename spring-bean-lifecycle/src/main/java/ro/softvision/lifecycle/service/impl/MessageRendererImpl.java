package ro.softvision.lifecycle.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.softvision.lifecycle.postprocess.BeanExecutionPostProcessor;
import ro.softvision.lifecycle.service.MessageProvider;
import ro.softvision.lifecycle.service.MessageRenderer;

public class MessageRendererImpl implements MessageRenderer {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeanExecutionPostProcessor.class);

    private MessageProvider messageProvider;


    @Override
    public void render() {
        final String message = this.messageProvider.provide();
        LOGGER.info(message);
    }

    @Override
    public void setMessageProvider(MessageProvider messageProvider) {
        this.messageProvider = messageProvider;
    }

}
