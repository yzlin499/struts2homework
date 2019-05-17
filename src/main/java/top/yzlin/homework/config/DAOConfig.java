package top.yzlin.homework.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.yzlin.homework.dao.TicketDAO;
import top.yzlin.homework.dao.UserDAO;
import top.yzlin.homework.database.DAOInvocationHandler;

import java.lang.reflect.Proxy;

@Configuration
public class DAOConfig {

    @Bean
    public UserDAO userDAO(@Autowired SessionFactory sessionFactory) {
        return createDAO(UserDAO.class, sessionFactory);
    }

    @Bean
    public TicketDAO ticketDAO(@Autowired SessionFactory sessionFactory) {
        return createDAO(TicketDAO.class, sessionFactory);
    }

    private <T> T createDAO(Class<T> daoInterface, SessionFactory sessionFactory) {
        return (T) Proxy.newProxyInstance(
                daoInterface.getClassLoader(),
                new Class[]{daoInterface},
                new DAOInvocationHandler(daoInterface, sessionFactory));
    }
}
