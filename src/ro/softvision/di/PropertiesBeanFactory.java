package ro.softvision.di;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesBeanFactory implements BeanFactory {

    private static final PropertiesBeanFactory INSTANCE;
    private static final Map<String, Object> BEANS_INSTANCES = new HashMap<>();
    private static final Map<Class<?>, Object> BEANS_CLASSES = new HashMap<>();

    static {
        INSTANCE = new PropertiesBeanFactory();
    }

    private PropertiesBeanFactory() {
        instantiateBeans();
    }

    private void instantiateBeans() {
        Properties properties = getProperties();
        for (Map.Entry e : properties.entrySet()) {
            try {
                String beanName = (String) e.getKey();
                Class<?> beanClass = Class.forName(properties.getProperty(beanName));
                Object beanInstance = beanClass.getConstructor().newInstance();
                BEANS_INSTANCES.put(beanName, beanInstance);
                BEANS_CLASSES.put(beanClass, beanInstance);
            } catch (NoSuchMethodException | ClassNotFoundException | IllegalAccessException | InstantiationException | InvocationTargetException ex) {
                ex.printStackTrace();
            }
        }
    }

    private Properties getProperties() {
        Properties properties = null;
        try {
            properties = new Properties();
            properties.load(PropertiesBeanFactory.class.getResourceAsStream("/dep.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static PropertiesBeanFactory getInstance() {
        return INSTANCE;
    }

    public Object getBean(String name) {
        return BEANS_INSTANCES.get(name);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> clazz) {
        return (T) BEANS_CLASSES.get(clazz);
    }

}
