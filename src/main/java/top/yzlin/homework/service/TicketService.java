package top.yzlin.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yzlin.homework.dao.TicketDAO;
import top.yzlin.homework.database.OperateQuery;
import top.yzlin.homework.entity.Ticket;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class TicketService {
    private final TicketDAO ticketDAO;

    public TicketService(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }

    public List<Ticket> ticketList() {
        return ticketDAO.ticketList();
    }

    public List<Ticket> ticketList(int page) {
        return ticketDAO.ticketList(page);
    }

    public List<Ticket> ticketList(int page, int size) {
        return ticketDAO.ticketList(page, size);
    }

    public long ticketSize() {
        return ticketDAO.ticketSize();
    }

    public List<Ticket> ticketList(OperateQuery operateQuery) {
        return ticketDAO.ticketList(operateQuery);
    }

    public int buyTicket(Ticket ticket) {
        return ticketDAO.buyTicket(ticket);
    }

    public void delectTicket(int ticketId) {
        ticketDAO.delectTicket(ticketId);
    }

    public void updateTicket(Ticket ticket) {
        ticketDAO.updateTicket(ticket);
    }

    public Ticket[] selectTicketByName(String name) {
        return ticketDAO.selectTicketByName(name);
    }
}
