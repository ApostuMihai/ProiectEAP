package com.company.entities;

import java.util.List;

public class User {
    private int id;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private List<Ticket> tickets;
    public User(){}

    public User(int id, String name, String phoneNumber, String email, String address, List<Ticket> tickets) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.tickets = tickets;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        String result =  "Name: " + this.name + "\n" + "Phone number: " + this.phoneNumber + "\n" + "Email address: " + this.email + "\n" + "Home address: " + this.address + "\n";
        for(int i = 0; i < this.tickets.size(); ++ i){
            if(tickets.get(i) instanceof FirstClass){
                FirstClass firstClass = (FirstClass) tickets.get(i);
                result += firstClass.toString();
            }
            else if(tickets.get(i) instanceof PrivateLounge){
                PrivateLounge privateLounge = (PrivateLounge) tickets.get(i);
                result += privateLounge.toString();
            } else if(tickets.get(i) instanceof SecondClass){
                SecondClass secondClass = (SecondClass) tickets.get(i);
                result += secondClass.toString();
            }
        }
        return result;
    }

}
