package ro.softvision.hibernate.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import ro.softvision.hibernate.entity.User;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:db/db.properties")
@Profile("dev")
public class HibernateConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateConfig.class);

    @Value("${driverClassName}")
    private String driverClassName;

    @Value("${url}")
    private String url;

    @Value("${db.username}")
    private String username;

    @Value("${password}")
    private String password;

    @Value("${classpath:db/schema.sql}")
    private Resource schemaScript;

    @Value("${classpath:db/test-data.sql}")
    private Resource dataScript;

    @Bean
    @Lazy
    public DataSource dataSource() {
        try {
            HikariConfig hikariConfig = new HikariConfig();

            //datasource connection data
            hikariConfig.setDriverClassName(driverClassName);
            hikariConfig.setJdbcUrl(url);
            hikariConfig.setUsername(username);
            hikariConfig.setPassword(password);

            //connection pool specific configuration
            hikariConfig.setMaximumPoolSize(5);
            hikariConfig.setConnectionTestQuery("SELECT 1");
            hikariConfig.setPoolName("springHikariCP");
            hikariConfig.addDataSourceProperty("dataSource.cachePrepStmts", "true");
            hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSize", "250");
            hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSqlLimit", "2048");
            hikariConfig.addDataSourceProperty("dataSource.useServerPrepStmts", "true");
            HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
            DatabasePopulatorUtils.execute(databasePopulator(), hikariDataSource);
            return hikariDataSource;
        } catch (Exception e) {
            LOGGER.error("Exception occurred at the creation of the connection", e);
            return null;
        }
    }

    @Bean
    public SessionFactory sessionFactory(DataSource dataSource, Properties hibernateProperties) {
        return new LocalSessionFactoryBuilder(dataSource)
                .addAnnotatedClasses(User.class)
                .addProperties(hibernateProperties)
                .buildSessionFactory();
    }

    @Bean
    public Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        hibernateProperties.put("hibernate.hbm2ddl.auto", "update");
        hibernateProperties.put("hibernate.format_sql", true);
        hibernateProperties.put("hibernate.use_sql_comments", true);
        hibernateProperties.put("hibernate.show_sql", true);
        return hibernateProperties;
    }

    @Bean
    public PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }

    private DatabasePopulator databasePopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(schemaScript);
        populator.addScript(dataScript);
        return populator;
    }

}
