package top.yzlin.homework.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.Properties;

@Configuration
@ComponentScan("top.yzlin.homework")
@PropertySource("classpath:config.properties")
@EnableTransactionManagement
public class MainConfig {

    @Bean
    LocalSessionFactoryBean sessionFactory(DataSource dataSource,
                                           @Value("${hibernate.dialect}") String dialect){
        LocalSessionFactoryBean sessionFactory= new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect",dialect);
        properties.setProperty("hbm2ddl.auto", "update");
        sessionFactory.setHibernateProperties(properties);
        sessionFactory.setPackagesToScan("top.yzlin.homework.entity");
        return sessionFactory;
    }

    @Bean
    HibernateTransactionManager transactionManager(SessionFactory sessionFactory){
        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
        hibernateTransactionManager.setSessionFactory(sessionFactory);
        return hibernateTransactionManager;
    }

    @Bean
    public DataSource dataSource(@Value("${db.driver}") String driver,
                                 @Value("${db.jdbcUrl}") String jdbcurl) throws PropertyVetoException, IOException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(driver);
        dataSource.setJdbcUrl(jdbcurl);
        return dataSource;
    }
}
