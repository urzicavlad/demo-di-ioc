package ro.softvision.spEL.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ValueBean {

    @Value("#{systemProperties['user.country']}")
    private String defaultLocale;

    private String operationSystem;

    private String importantDependency;

    public ValueBean(@Value("#{systemProperties['java.vm.specification.vendor']}") String importantDependency) {
        this.importantDependency = importantDependency;
    }

    public String getOperationSystem() {
        return operationSystem;
    }

    @Value("#{systemProperties['os.name']}")
    public void setOperationSystem(String operationSystem) {
        this.operationSystem = operationSystem;
    }

    public String getDefaultLocale() {
        return defaultLocale;
    }

    public void setDefaultLocale(String defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    public String getImportantDependency() {
        return importantDependency;
    }
}