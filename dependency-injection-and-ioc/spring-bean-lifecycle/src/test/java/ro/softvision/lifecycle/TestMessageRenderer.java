package ro.softvision.lifecycle;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ro.softvision.lifecycle.service.MessageRenderer;

import java.util.List;
import java.util.Map;

@ContextConfiguration(classes = {ApplicationConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("/test-application.properties")
@ActiveProfiles({"dev", "qa"})
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
        messageRenderer.renderMessage();
    }

    @Test
    public void getRandomNumber(){
        messageRenderer.renderRandomNumber();
    }

    @Test
    public void getOperationSystem(){
        messageRenderer.renderSystemOperation();
    }

    @Test
    public void testSpELTranslator(){
        ExpressionParser parser = new SpelExpressionParser();
        List list = (List) parser.parseExpression("{1,2,3,4}").getValue(applicationContext);
        System.out.println(list);

        Map map = (Map) parser.parseExpression("{name:'Nikola',dob:'10-July-1856'}").getValue(applicationContext);
        System.out.println(map);
    }




}
