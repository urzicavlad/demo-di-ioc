package ro.softvision.lifecycle;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ro.softvision.lifecycle.service.MessageRenderer;

@ContextConfiguration(classes = {ApplicationConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("/test-application.properties")
public class TestMessageRenderer {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private MessageRenderer messageRenderer;


    @After
    public void shutdownTheContainer() {
        ((ConfigurableApplicationContext) applicationContext).registerShutdownHook();
    }

    @Test
    public void testGetMessage() {
        messageRenderer.render();
    }

}
