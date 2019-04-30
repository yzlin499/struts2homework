package top.yzlin.homework.dao;

import top.yzlin.homework.database.OperateQuery;
import top.yzlin.homework.database.annotation.*;
import top.yzlin.homework.entity.Ticket;

import java.util.List;


public interface TicketDAO {

    default List<Ticket> ticketList(){
        return ticketList(q->{});
    }

    default List<Ticket> ticketList(int page){
        return ticketList(page,15);
    }

    default List<Ticket> ticketList(int page,int size){
        return ticketList(q-> q.setFirstResult((page-1)*size).setMaxResults(size));
    }

    @Select("select count(*) from Ticket")
    long ticketSize();

    @Select("from Ticket")
    List<Ticket> ticketList(OperateQuery operateQuery);

    @Save
    int buyTicket(Ticket ticket);

    @Delete("DELETE FROM Ticket WHERE ticketId=:ticketId")
    void delectTicket(@QueryParam("ticketId") int ticketId);

    @SaveOrUpdate
    void updateTicket(Ticket ticket);

    @Select("from Ticket t where t.name like :name")
    Ticket[] selectTicketByName(@QueryParam("name") String name);

}
