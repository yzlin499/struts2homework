package top.yzlin.homework.action;

import com.opensymphony.xwork2.ActionSupport;
import top.yzlin.homework.Context;
import top.yzlin.homework.dao.TicketDAO;
import top.yzlin.homework.entity.Ticket;

import java.util.Arrays;
import java.util.List;

public class SearchTicket extends ActionSupport {
    private String actionName;
    private TicketDAO ticketDAO;
    private List<Ticket> ticketList;
    private int page;
    private int pageSum;
    private String name;

    public String getActionName() {
        return "searchTicket";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public long getPage() {
        return page;
    }

    public int getPageSum() {
        return pageSum;
    }

    public SearchTicket() {
        ticketDAO= Context.getInstance().getComponent(TicketDAO.class);
    }

    @Override
    public String execute() throws Exception {
        Ticket[] tickets=ticketDAO.selectTicketByName("%"+name+"%");
        pageSum=(tickets.length+14)/15;
        page=Math.min(page,pageSum);
        page=Math.max(page,1);
        ticketList=Arrays.asList(Arrays.copyOfRange(tickets,(page-1)*15,Math.min(page*15,tickets.length)));
        return SUCCESS;
    }
}
