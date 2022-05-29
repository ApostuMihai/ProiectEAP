package com.company.entities;

import java.util.List;

public class Route {
    private int id;
    private List<Airplane> airplanes;
    private Airport destination;
    private Airport origin;
    double distance;

    public Route(){}

    public Route(int id, List<Airplane> airplanes, Airport destination, Airport origin, double distance) {
        this.id = id;
        this.airplanes = airplanes;
        this.destination = destination;
        this.origin = origin;
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Airplane> getAirplanes() {
        return airplanes;
    }

    public void setAirplanes(List<Airplane> airplanes) {
        this.airplanes = airplanes;
    }

    public Airport getDestination() {
        return destination;
    }

    public void setDestination(Airport destination) {
        this.destination = destination;
    }

    public Airport getOrigin() {
        return origin;
    }

    public void setOrigin(Airport origin) {
        this.origin = origin;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String toString(){
        String result = this.origin.toString();
        result += this.destination.toString();
        result += "Distance: " + this.distance + "\n";
        for(int i = 0; i < airplanes.size(); ++i){
            if(airplanes.get(i) instanceof Cargo){
                Cargo cargo = (Cargo) airplanes.get(i);
                result += cargo.toString();
            }
            else if(airplanes.get(i) instanceof PassengerAirplane){
                PassengerAirplane passengerAirplane = (PassengerAirplane) airplanes.get(i);
                result += passengerAirplane.toString();
            }
        }
        return result;
    }


}
