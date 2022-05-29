package com.company.entities;

public abstract class Airplane {
    protected int id;
    protected String name;
    protected double speed;
    protected int yearsOfFlying;
    protected double fuelCost;

    public Airplane(){}

    public Airplane(int id, String name, double speed, int yearsOfFlying, double fuelCost) {
        this.id = id;
        this.name = name;
        this.speed = speed;
        this.yearsOfFlying = yearsOfFlying;
        this.fuelCost = fuelCost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getyearsOfFlying() {
        return yearsOfFlying;
    }

    public void setyearsOfFlying(int yearsOfFlying) {
        this.yearsOfFlying = yearsOfFlying;
    }

    public double getFuelCost() {
        return fuelCost;
    }

    public void setFuelCost(double fuelCost) {
        this.fuelCost = fuelCost;
    }

}
