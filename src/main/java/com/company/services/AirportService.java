package com.company.services;

import com.company.entities.Airport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AirportService implements AirportInterface, CSVReaderWriter<Airport>{
    private ArrayList<Airport> airports = new ArrayList<>();

    private AirportService(){
        read();
    }
    private static AirportService instance;

    public static AirportService getInstance(){
        if(instance == null){
            instance = new AirportService();
        }
        return instance;
    }

    public ArrayList<Airport> getAirports() {
        ArrayList<Airport> airportsCopy = new ArrayList<>();
        airportsCopy.addAll(this.airports);
        return airportsCopy;
    }

    public Airport getAirportById(int index){
        Airport airport = new Airport();
        for(int i = 0; i < this.airports.size(); ++i){
            if(this.airports.get(i).getId() == index){
                airport = this.airports.get(i);
            }
        }
        return airport;
    }

    public void updateAirport(int index, Airport airport){
        for(int i = 0; i < this.airports.size(); ++i){
            if(this.airports.get(i).getId() == index){
                this.airports.remove(i);
                this.airports.add(index, airport);
                break;
            }
        }
    }

    public void addAirport(Airport airport){
        this.airports.add(airport);
    }

    public void deteleAirport(int index){
        for(int i = 0; i < this.airports.size(); ++i){
            if(this.airports.get(i).getId() == index) {
                this.airports.remove(i);
                break;
            }
        }
    }
    public Airport readAirport() throws ParseException {
        Scanner scanner = new Scanner(System.in);
        Airport airport = new Airport();
//        System.out.println("id = ");
//        airport.setId(scanner.nextInt());
        airport.setId(getMaxId() + 1);

        System.out.println("name = ");
        airport.setName(scanner.next());

        System.out.println("city = ");
        airport.setCity(scanner.next());

        System.out.println("country = ");
        airport.setCountry(scanner.next());

        System.out.println("date = ");
        String date = scanner.next();
        Date date_date = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        airport.setEstablishmentDate(date_date);

        return airport;
    }

    @Override
    public String getAntet() {
        return "Id,Name,City,Country,Establishment Date\n";
    }

    public int getMaxId(){
        int max = 0;
        for(int i = 0; i < airports.size(); ++i){
            if(airports.get(i).getId() > max){
                max = airports.get(i).getId();
            }
        }
        return max;
    }
    @Override
    public Airport processLine(String line) throws ParseException {
        // "Id,Name,City,Country,Establishment Date";
        // 1,Gara de Nord,Bucuresti,Romania,20/09/1960
        String[] fields = line.split(separator);
        int id = 0;
        if(Objects.equals(fields[0], "null")){
            id = getMaxId();
        } else {
            try {
                id = Integer.parseInt(fields[0]);
            } catch (Exception e) {
                System.out.println("The id must be an int");
            }
        }
        String name = fields[1];

        String city = fields[2];

        String country = fields[3];

        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(fields[4]);

        return new Airport(id, name, city, country, date);
    }

    @Override
    public String getFileName() {
        String path = "src/main/java/com/company/resources/data - Airport.csv";
        return path;
    }

    @Override
    public String convertObjectToString(Airport object) {
        Date date = object.getEstablishmentDate();
        String dateString = new SimpleDateFormat("dd/MM/yyyy").format(date);
        String line = object.getId() + separator + object.getName() + separator + object.getCity() + separator + object.getCountry() + separator+ dateString + "\n";
        return line;
    }

    @Override
    public void initList(List<Airport> objects) {
        airports = new ArrayList<Airport>(objects);
    }
}
