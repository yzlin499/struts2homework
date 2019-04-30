package top.yzlin.homework.action;

import com.opensymphony.xwork2.ActionSupport;
import top.yzlin.homework.Context;
import top.yzlin.homework.dao.TicketDAO;
import top.yzlin.homework.entity.Ticket;

public class ApiAction extends ActionSupport {
    private TicketDAO ticketDAO;
    private int ticketId;
    private Ticket ticket;

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public ApiAction(){
        ticketDAO= Context.getInstance().getComponent(TicketDAO.class);
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String deleteTicket() throws Exception {
        ticketDAO.delectTicket(ticketId);
        return SUCCESS;
    }

    public String changeTicket() throws Exception {
        ticketDAO.updateTicket(ticket);
        return SUCCESS;
    }
}
