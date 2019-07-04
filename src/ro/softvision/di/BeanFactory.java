package ro.softvision.di;

public interface BeanFactory {

    Object getBean(String name);

    <T> T getBean(Class<T> clazz);
}
