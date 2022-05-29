package com.company.services;

import com.company.entities.Airplane;

import java.util.ArrayList;

public interface AirplaneInterface {
    public ArrayList<Airplane> getAirplanes();

    public Airplane getTicketById(int index);

    public void updateAirplane(int index, Airplane airplane);

    public void addAirplane(Airplane airplane);

    public void deteleAirplane(int index);

    public Airplane readAirplane();
}
