package top.yzlin.homework.action;

import com.opensymphony.xwork2.ActionSupport;
import org.springframework.stereotype.Component;
import top.yzlin.homework.dao.TicketDAO;
import top.yzlin.homework.entity.Ticket;
import top.yzlin.homework.service.TicketService;

import java.util.List;

@Component
public class PlantTicket extends ActionSupport{
    private String actionName;
    private Ticket ticket;
    private final TicketService ticketService;
    private List<Ticket> ticketList;
    private long page;
    private long pageSum;

    public PlantTicket(TicketService ticketService) {
        this.ticketService = ticketService;
        pageSum=(ticketService.ticketSize()+14)/15;
    }

    public String getActionName() {
        return "showTicket";
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public String buyTicket(){
        return SUCCESS;
    }

    public void setPage(long page) {
        page=Math.max(page,1);
        page=Math.min(page,pageSum);
        this.page = page;
    }

    public long getPage() {
        return page=page<1?1:page;
    }

    public long getPageSum() {
        return pageSum;
    }

    public String doBuyTicket(){
        ticketService.buyTicket(ticket);
        pageSum=(ticketService.ticketSize()+14)/15;
        return SUCCESS;
    }

    public String showTicket(){
        page=page<1?1:page;
        ticketList=ticketService.ticketList((int) page);
        return SUCCESS;
    }



}
