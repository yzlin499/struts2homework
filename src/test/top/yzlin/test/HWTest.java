package top.yzlin.test;

import org.junit.Test;
import top.yzlin.homework.Context;
import top.yzlin.homework.dao.TicketDAO;
import top.yzlin.homework.dao.UserDAO;
import top.yzlin.homework.database.HibernateUtil;
import top.yzlin.homework.entity.Ticket;
import top.yzlin.homework.entity.User;

import java.lang.reflect.Method;
import java.util.Date;

public class HWTest {

    public static void main(String[] args) {
        Context context=Context.getInstance();
        TicketDAO component = context.getComponent(TicketDAO.class);
        System.out.println(component.ticketList(q->q.setFirstResult(60).setMaxResults(15)));

    }

    @Test
    public void hibernateTest() throws NoSuchMethodException {
//        Class<TicketDAO> ticketDAOClass = TicketDAO.class;
//        Method ticketList = ticketDAOClass.getMethod("ticketList");
//        ticketList.get


    }
}
