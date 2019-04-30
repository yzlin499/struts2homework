package top.yzlin.homework.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Entity
@Table(name="ticket_table")
public class Ticket {
    private int ticketId;
    private String name;
    private String sex;
    private String originating;
    private String destination;
    private Date departureDate;
    private String idCard;

    @Id
    @GeneratedValue
    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getOriginating() {
        return originating;
    }

    public void setOriginating(String originating) {
        this.originating = originating;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }


    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", originating='" + originating + '\'' +
                ", destination='" + destination + '\'' +
                ", departureDate=" + departureDate +
                ", idCard='" + idCard + '\'' +
                '}';
    }
}
