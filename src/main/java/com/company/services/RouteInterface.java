package com.company.services;

import com.company.entities.Route;
import com.company.entities.Airplane;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public interface RouteInterface {

    public ArrayList<Route> getRoutes();

    public Route getRouteById(int index);

    public void updateRoute(int index, Route route);

    public void addRoute(Route route);

    public void deteleRoute(int index);

    public Route readRoute() throws ParseException;
}
