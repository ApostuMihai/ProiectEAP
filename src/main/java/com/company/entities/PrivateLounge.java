package com.company.entities;

import java.util.ArrayList;
import java.util.TreeSet;

public class PrivateLounge extends FirstClass {
    // True => are, false n-are private lounge
    private boolean privateLounge;

    public PrivateLounge() {}

    public PrivateLounge(int id, double price, int seat, Route route, ArrayList<String> mealsIncluded, boolean privateLounge) {
        super(id, price, seat, route, new TreeSet<>(mealsIncluded));
        this.privateLounge = privateLounge;
    }

    public boolean itHasLounge() {
        return privateLounge;
    }

    public void setBedPosition(boolean privateLounge) {
        this.privateLounge = privateLounge;
    }

    @Override
    public String toString() {
        final String[] result = {"Price: " + this.price + "\n"};
        result[0] += "Seat: " + this.seat + "\n";
        result[0] += route.toString();
        result[0] += "Meals included:\n";
        final String[] meals = {""};
        mealsIncluded.forEach(meal -> {
            meals[0] += meal + "\n";
        });
        result[0] += meals[0];
        if(this.privateLounge){
            result[0] += "Has private lounge\n";
        } else {
            result[0] += "No private lounge\n";
        }
        return result[0];
    }
}
