package kz.epam.news.config;

import oracle.jdbc.driver.OracleDriver;
import org.apache.log4j.Logger;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@Configuration
@ComponentScan
@EnableTransactionManagement
@PropertySources({
        @PropertySource("classpath:database.properties"),
        @PropertySource("classpath:hibernate.properties")
})
public class HibernateConfig {

    public static final String CACHEABLE = "org.hibernate.cacheable";
    public static final Boolean CACHEABLE_FLAG = true;

    private static final String DIALECT = "hibernate.dialect";
    private static final String HBM2DLL = "hibernate.hbm2dll.auto";
    private static final String SHOW_SQL = "hibernate.show_sql";
    private static final String USE_SECOND_LVL_CACHE = "hibernate.cache.use_second_level_cache";
    private static final String CACHE_REGION = "hibernate.cache.region.factory_class";
    private static final String QUERY_CACHE = "hibernate.cache.use_query_cache";

    @Autowired
    private Environment environment;

    @Bean
    public DriverManagerDataSource newsDataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(OracleDriver.class.getName());
        dataSource.setUrl(environment.getProperty("database.url"));
        dataSource.setUsername(environment.getProperty("database.username"));
        dataSource.setPassword(environment.getProperty("database.password"));
        dataSource.setSchema(environment.getProperty("database.schema"));

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        LocalContainerEntityManagerFactoryBean embFactory = new LocalContainerEntityManagerFactoryBean();

        embFactory.setDataSource(newsDataSource());
        embFactory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        embFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        embFactory.setJpaProperties(jpaProperties());
        embFactory.setPackagesToScan("kz.epam");

        return embFactory;
    }

    @Bean
    public JpaTransactionManager transactionManager() {

        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public Logger logger() {
        return Logger.getLogger(this.getClass());
    }

    private Properties jpaProperties() {

        Properties jpaProperties = new Properties();

        jpaProperties.setProperty(DIALECT, environment.getProperty(DIALECT));
        jpaProperties.setProperty(HBM2DLL, environment.getProperty(HBM2DLL));
        jpaProperties.setProperty(SHOW_SQL, environment.getProperty(SHOW_SQL));
        jpaProperties.setProperty(USE_SECOND_LVL_CACHE, environment.getProperty(USE_SECOND_LVL_CACHE));
        jpaProperties.setProperty(CACHE_REGION, environment.getProperty(CACHE_REGION));
        jpaProperties.setProperty(QUERY_CACHE, environment.getProperty(QUERY_CACHE));

        return jpaProperties;
    }
}