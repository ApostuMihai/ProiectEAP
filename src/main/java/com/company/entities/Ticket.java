package com.company.entities;

import com.company.entities.Route;

public abstract class Ticket {
    protected int id;
    protected double price;
    protected int seat;
    protected Route route;

    public Ticket(){}

    public Ticket(int id, double price, int seat, Route route) {
        this.id = id;
        this.price = price;
        this.seat = seat;
        this.route = route;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
