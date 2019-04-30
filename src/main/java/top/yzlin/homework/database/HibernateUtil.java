package top.yzlin.homework.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import top.yzlin.homework.ioc.ComponentInit;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;

public class HibernateUtil implements ComponentInit {
    private SessionFactory sessionFactory;

    @Override
    public void init() {
        Configuration cfg = new Configuration();
        cfg.configure();
        sessionFactory = cfg.buildSessionFactory();
    }

    public <T> T getSession(Function<Session,T> sessionConsumer){
        Session session = sessionFactory.openSession();
        T apply = sessionConsumer.apply(session);
        if (session.isOpen()) {
            session.close();
        }
        return apply;
    }

    public <T> T getSessionWithTransaction(Function<Session,T> sessionConsumer){
        return getSession(s->{
            Transaction transaction = s.beginTransaction();
            T apply = sessionConsumer.apply(s);
            transaction.commit();
            return apply;
        });
    }

    public <T> T createDAO(Class<T> daoInterface){
        return (T) Proxy.newProxyInstance(
                daoInterface.getClassLoader(),
                new Class[]{daoInterface},
                new DAOInvocationHandler(daoInterface,this));
    }
}
