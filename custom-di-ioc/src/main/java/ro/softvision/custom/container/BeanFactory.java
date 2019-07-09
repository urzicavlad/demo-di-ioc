package ro.softvision.custom.container;

public interface BeanFactory {

    Object getBean(String name);

    <T> T getBean(Class<T> clazz);
}
