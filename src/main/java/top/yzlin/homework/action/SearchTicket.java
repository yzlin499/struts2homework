package top.yzlin.homework.action;

import com.opensymphony.xwork2.ActionSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.yzlin.homework.dao.TicketDAO;
import top.yzlin.homework.entity.Ticket;
import top.yzlin.homework.service.TicketService;

import java.util.Arrays;
import java.util.List;

@Component
public class SearchTicket extends ActionSupport {
    private String actionName;
    private final TicketService ticketService;
    private List<Ticket> ticketList;
    private int page;
    private int pageSum;
    private String name;

    public SearchTicket(TicketService ticketService) {
        this.ticketService = ticketService;
    }

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

    @Override
    public String execute() throws Exception {
        Ticket[] tickets=ticketService.selectTicketByName("%"+name+"%");
        pageSum=(tickets.length+14)/15;
        page=Math.min(page,pageSum);
        page=Math.max(page,1);
        ticketList=Arrays.asList(Arrays.copyOfRange(tickets,(page-1)*15,Math.min(page*15,tickets.length)));
        return SUCCESS;
    }
}
