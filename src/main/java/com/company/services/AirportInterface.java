package com.company.services;

import com.company.entities.Airport;

import java.text.ParseException;
import java.util.ArrayList;

public interface AirportInterface {
    public ArrayList<Airport> getAirports();

    public Airport getAirportById(int index);

    public void updateAirport(int index, Airport airport);

    public void addAirport(Airport airport);

    public void deteleAirport(int index);

    Airport readAirport() throws ParseException;
}
