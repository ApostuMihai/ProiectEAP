package com.company.services;

import com.company.entities.*;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class RouteService implements RouteInterface, CSVReaderWriter<Route>{
    private ArrayList<Route> routes = new ArrayList<>();

    private RouteService(){
        read();
    }
    private static RouteService instance;

    public static RouteService getInstance(){
        if(instance == null){
            instance = new RouteService();
        }
        return instance;
    }

    public ArrayList<Route> getRoutes() {
        ArrayList<Route> routesCopy = new ArrayList<>();
        routesCopy.addAll(this.routes);
        return routesCopy;
    }

    public Route getRouteById(int index){
        Route route = new Route();
        for(int i = 0; i < this.routes.size(); ++i){
            if(this.routes.get(i).getId() == index){
                route = this.routes.get(i);
            }
        }
        return route;
    }

    public void updateRoute(int index, Route route){
        for(int i = 0; i < this.routes.size(); ++i){
            if(this.routes.get(i).getId() == index){
                this.routes.remove(i);
                this.routes.add(index, route);
                break;
            }
        }
    }

    public void addRoute(Route route){
        this.routes.add(route);
    }

    public void deteleRoute(int index){
        for(int i = 0; i < this.routes.size(); ++i){
            if(this.routes.get(i).getId() == index) {
                this.routes.remove(i);
                break;
            }
        }
    }

    public Route readRoute() throws ParseException {
        Route route = new Route();
        Scanner scanner = new Scanner(System.in);
        AirplaneService airplaneService = AirplaneService.getInstance();
        AirportService airportService = AirportService.getInstance();
        System.out.println("id = ");
        route.setId(scanner.nextInt());

        List<Airplane> airplanes = new ArrayList<>();
        System.out.println("number of airplanes = ");
        int nr = scanner.nextInt();
        System.out.println("airplanes = ");
        for(int i = 0; i < nr; ++i){
            Airplane airplane = airplaneService.readAirplane();
            airplanes.add(airplane);
            airplaneService.addAirplane(airplane);
        }
        route.setAirplanes(airplanes);

        System.out.println("destination = ");
        Airport dst = airportService.readAirport();
        route.setDestination(dst);
        airportService.addAirport(dst);

        System.out.println("origin = ");
        Airport src = airportService.readAirport();
        airportService.addAirport(src);
        route.setOrigin(src);

        System.out.println("distance = ");
        route.setDistance(scanner.nextDouble());

        return route;
    }

    @Override
    public String getAntet() {
        return "";
    }

    @Override
    public Route processLine(String line) throws ParseException {
        //Id,Destination Airport Id,Origin Airport Id,Distance
        String[] fields = line.split(separator);
        //System.out.println(line);
        int id = 0;
        try{
            id = Integer.parseInt(fields[0]);
        } catch (Exception e){
            System.out.println("The id must be an int");
        }

        int idDest = 0;
        try{
            idDest = Integer.parseInt(fields[1]);
        } catch (Exception e){
            System.out.println("The id must be an int");
        }

        int idSrc = 0;
        try{
            idSrc = Integer.parseInt(fields[2]);
        } catch (Exception e){
            System.out.println("The id must be an int");
        }
        double dist = 0.0;
        try{
            dist = Double.parseDouble(fields[3]);
        } catch (Exception e){
            System.out.println("The dist must be a double");
        }
        AirportService airportService = AirportService.getInstance();
        Airport dest = new Airport();
        Airport src = new Airport();
        dest.setId(-1);
        src.setId(-1);
        try{
            dest = airportService.getAirportById(idDest);
        } catch (Exception e){
            System.out.println("The airport doesnt exist");
        }

        try{
            src = airportService.getAirportById(idSrc);
        } catch (Exception e){
            System.out.println("The airport doesnt exist");
        }
        return new Route(id, new ArrayList<>(), dest, src, dist);
    }

    @Override
    public String getFileName() {
        return "src/main/java/com/company/resources/data - Route.csv";
    }

    @Override
    public String convertObjectToString(Route object) {
        String res1;
        if(object.getDestination().getId() == -1){
            res1 = "null";
        } else {
            res1 = String.valueOf(object.getDestination().getId());
        }

        String res2;
        if(object.getOrigin().getId() == -1){
            res2= "null";
        } else {
            res2= String.valueOf(object.getDestination().getId());
        }
        String line = object.getId() + separator + res1 + separator + res2 + separator + object.getDistance() + "\n";
        return line;
    }

    @Override
    public void initList(List<Route> objects) {
        routes = new ArrayList<>(objects);
    }

    @Override
    public List<Route> read(){
        String fileName = this.getFileName();
        File file = new File(fileName);
        String extraFileName = "src/main/java/com/company/resources/data - Route_Info.csv";
        File extraFile = new File(extraFileName);

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            List<Route> result;

            try {
                List<Route> resultLines = new ArrayList<Route>();
                bufferedReader.readLine(); // skip first line
                String currentLine = bufferedReader.readLine();

                while (true) {
                    if (currentLine == null) {
                        result = resultLines;
                        break;
                    }
                    Route obj = this.processLine(currentLine);
                    resultLines.add(obj);
                    currentLine = bufferedReader.readLine();
                }
                BufferedReader extra = new BufferedReader(new FileReader(extraFile));
                try{
                    extra.readLine();
                    String line = extra.readLine();
                    while (true) {
                        if (line == null) {
                            break;
                        }
                        String[] fields = line.split(separator);
                        int id = Integer.parseInt(fields[0]);
                        Route route = resultLines.stream()
                                .filter(r -> r.getId() == id)
                                .findAny()
                                .orElse(null);
                        if(route != null){
                            if(route.getAirplanes().size() == 0){
                                List<Airplane> objs = new ArrayList<Airplane>();
                                int idAirplane = -1;
                                try{
                                    idAirplane = Integer.parseInt(fields[1]);
                                } catch (Exception e){
                                    System.out.println("The id must be an int");
                                }
                                AirplaneService airplaneService = AirplaneService.getInstance();
                                for(int i = 0; i < airplaneService.getAirplanes().size(); ++i){
                                    Airplane airplane = airplaneService.getAirplanes().get(i);
                                    if(airplane.getId() == idAirplane){
                                        if(airplane instanceof Cargo){
                                            Cargo cargo = (Cargo) airplane;
                                            objs.add(cargo);
                                        } else if(airplane instanceof PassengerAirplane){
                                            PassengerAirplane pass = (PassengerAirplane) airplane;
                                            objs.add(pass);
                                        }
                                        break;
                                    }
                                }
                                route.setAirplanes(objs);
                            } else {
                                List<Airplane> objs = route.getAirplanes();
                                int idAirplane = -1;
                                try{
                                    idAirplane = Integer.parseInt(fields[1]);
                                } catch (Exception e){
                                    System.out.println("The id must be an int");
                                }
                                AirplaneService airplaneService = AirplaneService.getInstance();
                                for(int i = 0; i < airplaneService.getAirplanes().size(); ++i){
                                    Airplane airplane = airplaneService.getAirplanes().get(i);
                                    if(airplane.getId() == idAirplane){
                                        if(airplane instanceof Cargo){
                                            Cargo cargo = (Cargo) airplane;
                                            objs.add(cargo);
                                        } else if(airplane instanceof PassengerAirplane){
                                            PassengerAirplane pass = (PassengerAirplane) airplane;
                                            objs.add(pass);
                                        }
                                        break;
                                    }
                                }
                                route.setAirplanes(objs);
                            }

                            int index = 0;
                            for(Route element : resultLines){
                                if(element.getId() == route.getId()){
                                    resultLines.set(index, route);
                                    break;
                                }
                                index += 1;
                            }
                        }

                        line = extra.readLine();
                    }
                } catch (Throwable t){
                    try {
                        extra.close();
                    } catch (Throwable s) {
                        t.addSuppressed(s);
                    }
                    throw t;
                }
                result = resultLines;

            } catch (Throwable anything) {
                try {
                    bufferedReader.close();
                } catch (Throwable something) {
                    anything.addSuppressed(something);
                }
                throw anything;
            }

            //bufferedReader.close();
            initList(result);
            return result;
        } catch (FileNotFoundException e1) {
            System.out.println("File not found");
            initList(Collections.emptyList());
            return Collections.emptyList();
        } catch (IOException | ParseException e2) {
            System.out.println("Cannot read from file");
            initList(Collections.emptyList());
            return Collections.emptyList();
        }
    }

    @Override
    public void write(List<Route> objects){
        String fileName = this.getFileName();
        File file = new File(fileName);

        try{
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, false));
            try{
                String CSVline = "Id,Destination Airport Id,Origin Airport Id,Distance\n";
                bufferedWriter.write(CSVline);
            } catch (Throwable anything){
                throw anything;
            }
            if(objects != null){
                for(Route object : objects){
                    try{
                        String CSVline = this.convertObjectToString(object);
                        bufferedWriter.write(CSVline);
                    } catch (Throwable anything){
                        throw anything;
                    }
                }

            }
            bufferedWriter.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        fileName = "src/main/java/com/company/resources/data - Route_Info.csv";
        file = new File(fileName);

        try{
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, false));
            try{
                String CSVline = "Id,Airplane Id\n";
                bufferedWriter.write(CSVline);
            } catch (Throwable anything){
                throw anything;
            }
            if(objects != null){
                for(Route object : objects){
                    List<Airplane> objs = object.getAirplanes();
                    int nr = objs.size();
                    while(nr > 0){
                        String CSVline = object.getId() + separator;
                        if(objs.size() > 0){
                            CSVline += objs.get(0).getId();
                            objs.remove(0);
                        } else {
                            CSVline += "null";
                        }

                        try{
                            CSVline +=  "\n";
                            bufferedWriter.write(CSVline);
                        } catch (Throwable anything){
                            throw anything;
                        }
                        nr -= 1;
                    }
                }

            }
            bufferedWriter.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
