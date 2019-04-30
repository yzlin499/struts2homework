package top.yzlin.homework.action;

import com.opensymphony.xwork2.ActionSupport;
import top.yzlin.homework.Context;
import top.yzlin.homework.dao.TicketDAO;
import top.yzlin.homework.entity.Ticket;

import java.util.List;

public class PlantTicket extends ActionSupport{
    private String actionName;
    private Ticket ticket;
    private TicketDAO ticketDAO;
    private List<Ticket> ticketList;
    private long page;
    private long pageSum;

    public PlantTicket() {
        ticketDAO= Context.getInstance().getComponent(TicketDAO.class);
        pageSum=(ticketDAO.ticketSize()+14)/15;
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
        ticketDAO.buyTicket(ticket);
        pageSum=(ticketDAO.ticketSize()+14)/15;
        return SUCCESS;
    }

    public String showTicket(){
        page=page<1?1:page;
        ticketList=ticketDAO.ticketList((int) page);
        return SUCCESS;
    }



}
