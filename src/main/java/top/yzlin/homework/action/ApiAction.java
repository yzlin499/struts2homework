package top.yzlin.homework.action;

import com.opensymphony.xwork2.ActionSupport;
import org.springframework.stereotype.Component;
import top.yzlin.homework.entity.Ticket;
import top.yzlin.homework.service.TicketService;

/**
 * @author yzlin
 */
@Component
public class ApiAction extends ActionSupport {
    private final TicketService ticketService;
    private int ticketId;
    private Ticket ticket;

    public ApiAction(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String deleteTicket() throws Exception {
        ticketService.delectTicket(ticketId);
        return SUCCESS;
    }

    public String changeTicket() throws Exception {
        ticketService.updateTicket(ticket);
        return SUCCESS;
    }
}
