package com.company.entities;

import java.util.TreeSet;

public class FirstClass extends Ticket {
    protected TreeSet<String> mealsIncluded;

    public FirstClass() {}

    public TreeSet<String> getMealsIncluded() {
        return mealsIncluded;
    }

    public void setMealsIncluded(TreeSet<String> mealsIncluded) {
        this.mealsIncluded = mealsIncluded;
    }


    public FirstClass(int id, double price, int seat, Route route, TreeSet<String> mealsIncluded) {
        super(id, price, seat, route);
        this.mealsIncluded = mealsIncluded;
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
        return result[0];
    }
}
