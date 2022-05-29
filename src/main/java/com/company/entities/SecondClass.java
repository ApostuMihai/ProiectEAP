package com.company.entities;

public class SecondClass extends Ticket {
    private double discount;

    public SecondClass() {}

    public SecondClass(int id, double price, int seat, Route route, double discount) {
        super(id, price, seat, route);
        this.discount = discount;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        final String[] result = {"Price: " + this.price + "\n"};
        result[0] += "Seat: " + this.seat + "\n";
        result[0] += route.toString();
        result[0] += "Discount: " + this.discount + "\n";
        return result[0];
    }
}
