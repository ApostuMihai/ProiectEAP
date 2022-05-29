package com.company.services;

import com.company.entities.Ticket;

import java.text.ParseException;
import java.util.ArrayList;

public interface TicketInterface {
    public ArrayList<Ticket> getTickets();

    public Ticket getTicketById(int index);

    public void updateTicket(int index, Ticket ticket);

    public void addTicket(Ticket ticket);

    public void deteleTicket(int index);

    public Ticket readTicket() throws ParseException;
}
