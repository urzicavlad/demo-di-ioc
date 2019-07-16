package ro.softvision.aop;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.FixedValue;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ro.softvision.aop.service.MessageProvider;
import ro.softvision.aop.service.MessageProviderImpl;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
@TestPropertySource("classpath:test-application.properties")
public class MessageProviderImplTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private MessageProvider messageProvider;


    @After
    public void tearDown() {
        ((ConfigurableApplicationContext)context).registerShutdownHook();
    }

    @Test
    public void provideMessageShouldReturnACapitalizedMessage() {
        String message = messageProvider.provideMessage();
        assertEquals("TEST HELLO WORLD!", message);
    }

    @Test
    public void testCGLIBProxy() {

        messageProvider.provideMessage();
        System.out.println("messageProvider.provideMessage() = " + messageProvider.provideMessage());

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(MessageProvider.class);
        enhancer.setCallback((FixedValue) () -> "hi form CGLIB proxy");
        MessageProvider proxy = (MessageProvider) enhancer.create();
        System.out.println("proxy.doStuff() = " + proxy.provideMessage());

    }

}