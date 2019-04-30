package top.yzlin.homework;

import top.yzlin.homework.dao.TicketDAO;
import top.yzlin.homework.dao.UserDAO;
import top.yzlin.homework.database.*;

public class ContextConfig {
    public final static Class[] COMPONENTS = new Class[]{
            HibernateUtil.class,
            RepositoryManagement.class
    };

    public final static Class[] REPOSITORY = new Class[]{
            TicketDAO.class,
            UserDAO.class
    };
}
