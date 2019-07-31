package ro.softvision.datajpa.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Import(DataSourceConfig.class)
@EnableJpaRepositories("ro.softvision.datajpa.repository")
public class PersistenceConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceConfig.class);

    private final DataSource dataSource;
    private final Properties hibernateProperties;

    public PersistenceConfig(DataSource dataSource, Properties hibernateProperties) {
        this.dataSource = dataSource;
        this.hibernateProperties = hibernateProperties;
    }

    @Bean
    public static PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }

    @Bean
    public  HibernateJpaVendorAdapter hibernateJpaVendorAdapter(){
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public EntityManagerFactory entityManagerFactory(PersistenceUnitManager persistenceUnitManager,
                                                     HibernateJpaVendorAdapter hibernateJpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean factoryBean =
                new LocalContainerEntityManagerFactoryBean();

        factoryBean.setPersistenceUnitManager(persistenceUnitManager);
        factoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        factoryBean.setJpaProperties(hibernateProperties);
        factoryBean.afterPropertiesSet();

        return factoryBean.getNativeEntityManagerFactory();
    }

    @Bean
    public PersistenceUnitManager persistenceUnitManager(){
        DefaultPersistenceUnitManager
                persistenceUnitManager = new DefaultPersistenceUnitManager();

        persistenceUnitManager.setPackagesToScan("ro.softvision.datajpa.entity");
        persistenceUnitManager.setDefaultDataSource(dataSource);

        return persistenceUnitManager;
    }

    @Bean
    public PlatformTransactionManager jpaTxManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
